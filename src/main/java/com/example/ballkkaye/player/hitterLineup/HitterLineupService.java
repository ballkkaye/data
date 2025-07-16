package com.example.ballkkaye.player.hitterLineup;

import com.example.ballkkaye._core.error.ex.Exception404;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.player.Player;
import com.example.ballkkaye.player.PlayerRepository;
import com.example.ballkkaye.player.startingPitcher.today.TodayStartingPitcherRepository;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.TeamRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.ballkkaye._core.util.Util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class HitterLineupService {
    private final HitterLineupRepository hitterLineUpRepository;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;


    /**
     * 오늘 날짜 기준으로 경기 시작 1시간 전부터 경기 시작 직전 사이의 경기만 대상으로 타자 라인업을 크롤링하고 저장
     * - 경기 시작 시간이 현재 시각과 비교하여 1시간 이내일 때만 실행
     */
    @Transactional
    public void crawlHitterLineUpsByGameTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Game> todayGames = gameRepository.findByToday(); // 오늘 경기만 조회

        if (todayGames.isEmpty()) {
            log.info("오늘 경기 없음 → 크롤링 생략");
            return;
        }

        int total = todayGames.size();
        int eligible = 0;
        int skipped = 0;

        for (Game game : todayGames) {
            LocalDate date = game.getGameTime().toLocalDateTime().toLocalDate();
            Integer homeTeamId = game.getHomeTeam().getId();
            Integer awayTeamId = game.getAwayTeam().getId();

            // [조건] 경기 시작 1시간 전부터 시작 전까지만 허용
            LocalDateTime gameTime = game.getGameTime().toLocalDateTime();
            if (!now.isBefore(gameTime.minusHours(1)) && now.isBefore(gameTime)) {
                eligible++;
                log.info("[대상] 크롤링 가능 - %s vs %s (%s)%n",
                        game.getAwayTeam().getTeamName(),
                        game.getHomeTeam().getTeamName(),
                        gameTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                crawlAndSaveSingleGameLineup(game);
            } else {
                skipped++;
                log.info("[SKIP] 크롤링 시간 아님 - %s vs %s (%s)%n",
                        game.getAwayTeam().getTeamName(),
                        game.getHomeTeam().getTeamName(),
                        gameTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        }
        log.info("전체 경기 수: %d, 크롤링 대상: %d, 스킵됨: %d%n", total, eligible, skipped);
    }

    /**
     * 단일 경기(Game)에 대해 타자 라인업을 크롤링하고, 존재할 경우 DB에 저장
     * - 실제 크롤링은 crawlSingleGameLineup()
     * - 저장은 saveLineups()에서 처리
     */
    @Transactional
    public void crawlAndSaveSingleGameLineup(Game game) {
        List<HitterLineup> entities = crawlSingleGameLineup(game);
        if (!entities.isEmpty()) {
            saveLineups(entities);
            log.info("라인업 저장 완료 → gameId=%d, 라인업 수: %d%n", game.getId(), entities.size());
        } else {
            log.info("라인업 없음 또는 크롤링 실패 → gameId=%d%n", game.getId());
        }
    }


    /**
     * 크롤링된 타자 라인업 리스트를 DB에 일괄 저장
     * - JPA persist 로직을 내부 Repository에 위임
     */
    @Transactional
    public void saveLineups(List<HitterLineup> hitterLineups) {
        hitterLineUpRepository.saveAll(hitterLineups);
    }


    /**
     * 특정 경기의 타자 라인업을 크롤링하여 DB 저장 가능한 HitterLineup 객체 리스트로 반환
     * - KBO 메인 페이지에서 해당 경기 요소를 찾아 클릭 후 라인업 탭에 접근
     * - 라인업이 발표되었을 경우, 홈팀/원정팀 각각의 타자 라인업을 크롤링
     */
    public List<HitterLineup> crawlSingleGameLineup(Game game) {
        List<HitterLineup> allEntities = new ArrayList<>();

        // [0] Selenium WebDriver 설정 (Headless 모드)
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // [1] KBO 메인 페이지 접속 및 경기 리스트 로딩 대기
            driver.get("https://www.koreabaseball.com/Default.aspx?vote=true");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.game-cont")));

            // [2] 경기 정보 기준값 준비 (날짜, 홈팀/원정팀명, 구장명)
            String dateStr = game.getGameTime().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // ex) 20250709
            String homeTeam = game.getHomeTeam().getTeamName().split(" ")[0]; // ex) SSG
            String awayTeam = game.getAwayTeam().getTeamName().split(" ")[0]; // ex) KT
            String stadiumName = simplifyStadiumName(game.getStadium().getStadiumName());  // ex) 잠실

            // [3] 해당 경기 요소 찾기 (일치하는 game-cont 요소 필터링)
            WebElement targetGameElement = driver.findElements(By.cssSelector("li.game-cont")).stream()
                    .filter(el ->
                            dateStr.equals(el.getAttribute("g_dt")) &&
                                    homeTeam.equals(el.getAttribute("home_nm")) &&
                                    awayTeam.equals(el.getAttribute("away_nm")) &&
                                    stadiumName.equals(el.getAttribute("s_nm"))
                    )
                    .findFirst()
                    .orElseThrow(() -> new Exception404("경기 요소를 찾을 수 없습니다"));

            // [4] 경기 취소 여부 확인
            String statusText = targetGameElement.findElement(By.cssSelector("p.staus")).getText().trim();
            if (statusText.contains("경기취소")) return allEntities;

            // [5] 팀 ID 및 선발투수 정보 로드
            Integer homeTeamId = game.getHomeTeam().getId();
            Integer awayTeamId = game.getAwayTeam().getId();
            List<String> homePitchers = todayStartingPitcherRepository.findByGameIdAndTeamName(game.getId(), homeTeam);
            if (homePitchers.isEmpty()) throw new Exception404("홈팀 선발투수 정보 없음");
            String homePitcher = homePitchers.get(0);
            List<String> awayPitchers = todayStartingPitcherRepository.findByGameIdAndTeamName(game.getId(), awayTeam);
            if (awayPitchers.isEmpty()) throw new Exception404("원정팀 선발투수 정보 없음");
            String awayPitcher = awayPitchers.get(0);

            // [6] 경기 상세 진입 → 라인업 탭 클릭
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetGameElement.findElement(By.cssSelector("a#btnGame")));
            Thread.sleep(2000);

            WebElement lineupTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@id='tabGame']//a[contains(text(),'라인업')]")));
            lineupTab.click();
            Thread.sleep(2000);

            // [7] 라인업 발표 여부 확인
            boolean lineupAnnounced = !driver.findElements(By.cssSelector("#tableHitterB tbody tr")).isEmpty()
                    || !driver.findElements(By.cssSelector("#tableHitterT tbody tr")).isEmpty();

            if (!lineupAnnounced) {
                log.info(" [%s vs %s] 라인업 아직 미발표 → 생략 (gameId=%d)\n",
                        game.getAwayTeam().getTeamName(),
                        game.getHomeTeam().getTeamName(),
                        game.getId());
            }

            // [8] 홈팀/원정팀 라인업 추출
            if (!driver.findElements(By.cssSelector("#tableHitterB tbody tr")).isEmpty()) {
                allEntities.addAll(extractLineups(driver, wait, game, homeTeamId, homeTeam, awayTeam, awayPitcher, "#tableHitterB tbody tr"));
            }
            if (!driver.findElements(By.cssSelector("#tableHitterT tbody tr")).isEmpty()) {
                allEntities.addAll(extractLineups(driver, wait, game, awayTeamId, awayTeam, homeTeam, homePitcher, "#tableHitterT tbody tr"));
            }

        } catch (Exception e) {
            log.error("타자 라인업 크롤링 실패 - gameId=" + game.getId());
        } finally {
            driver.quit();
        }

        return allEntities;
    }


    /**
     * HTML 테이블에서 타자 라인업 정보를 파싱하여 HitterLineup 엔티티로 변환
     * - 각 행마다 타자 이름, 포지션, 시즌 AVG, 상대투수와의 맞대결 정보를 추출
     * - 선수/팀 엔티티는 DB에서 조회하여 연결
     */
    private List<HitterLineup> extractLineups(WebDriver driver, WebDriverWait wait, Game game,
                                              Integer teamId, String hitterTeam, String opponentTeam, String opponentPitcher,
                                              String rowSelector) {
        List<HitterLineup> entities = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.cssSelector(rowSelector));  // 라인업 테이블 행(row) 가져오기

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.cssSelector("th, td"));
            if (cols.size() < 4) continue;  // 잘못된 row는 건너뜀

            // [1] 타자 이름, 포지션, 시즌 타율 추출
            String hitterName = cols.get(2).getText().replaceAll("\\s+", "").trim(); // 이름의 모든 공백 제거
            String position = cols.get(1).getText().trim();  // 포지션
            Double seasonAVG = parseSeasonAVG(cols);  // 시즌 타율

            // [2] DB에서 선수 및 팀 엔티티 조회 (try-catch로 감싸서 누가 문제인지 명확하게 로그 남기기)
            try {
                Player player = playerRepository.findByNameAndTeamId(hitterName, teamId)
                        .orElseThrow(() -> new Exception404("선수를 찾을 수 없습니다. : " + hitterName + ", teamId=" + teamId));
                Team team = teamRepository.findById(teamId)
                        .orElseThrow(() -> new Exception404("팀을 찾을 수 없습니다. : teamId=" + teamId));

                // [3] 해당 타자와 상대 투수 간의 맞대결 전적 조회
                HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO matchup =
                        crawlMatchup(opponentTeam, opponentPitcher, hitterTeam, hitterName);

                // [4] HitterLineup 엔티티 생성 및 리스트에 추가
                entities.add(HitterLineup.builder()
                        .game(game)
                        .team(team)
                        .player(player)
                        .hitterOrder(i + 1)  // 타순은 1부터 시작
                        .position(position)
                        .seasonAvg(seasonAVG)
                        .ab(matchup != null ? matchup.getAb() : null)
                        .h(matchup != null ? matchup.getH() : null)
                        .avg(matchup != null ? matchup.getAvg() : null)
                        .ops(matchup != null ? matchup.getOps() : null)
                        .build());

            } catch (Exception e) {
                log.error("선수 라인업 저장 실패 → 이름: %s, 팀 ID: %d, 메시지: %s%n", hitterName, teamId, e.getMessage());
            }
        }

        return entities;
    }


    /**
     * 특정 타자와 투수의 맞대결 전적을 KBO 사이트에서 크롤링하여 DTO로 반환
     */
    private HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO crawlMatchup(
            String pitcherTeamNm, String pitcherNm, String hitterTeamNm, String hitterNm) {

        // [1] Selenium WebDriver 설정 (Headless 모드)
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // [2] KBO 맞대결 전적 페이지 접속
            driver.get("https://www.koreabaseball.com/Record/Etc/HitVsPit.aspx");

            // [3] 투수 팀 선택
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlPitcherTeam")))), pitcherTeamNm);

            // [4] 투수 드롭다운 목록 로딩 대기 (해당 투수 이름 포함 여부 확인)
            wait.until(d -> {
                Select s = new Select(d.findElement(By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")));
                return s.getOptions().stream()
                        .anyMatch(opt -> opt.getText().contains(pitcherNm));
            });

            // [5] 투수 선택
            Select pitcherSelect = new Select(driver.findElement(By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")));
            selectByContainingText(pitcherSelect, pitcherNm);

            // [6] 타자 팀 선택
            Select hitterTeamSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterTeam"))));
            selectByContainingText(hitterTeamSelect, hitterTeamNm);

            // [7] 타자 드롭다운 로딩 대기 (타자 이름 포함 여부 확인)
            wait.until(d -> {
                Select s = new Select(d.findElement(
                        By.id("cphContents_cphContents_cphContents_ddlHitterPlayer")));
                return s.getOptions().stream()
                        .anyMatch(opt -> opt.getText().contains(hitterNm));
            });

            // [8] 타자 선택
            Select hitterSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterPlayer"))));
            selectByContainingText(hitterSelect, hitterNm);

            // [9] 조회 버튼 클릭
            driver.findElement(By.id("cphContents_cphContents_cphContents_btnSearch")).click();

            // [10] 전적 테이블 로딩 대기
            wait.until(d -> Jsoup.parse(d.getPageSource())
                    .selectFirst("table.tData.tt tbody tr") != null);

            // [11] Jsoup으로 테이블 파싱
            Document doc = Jsoup.parse(driver.getPageSource());
            Element noRecordCell = doc.selectFirst("table.tData.tt tbody tr td[colspan=15]");

            // [12] 전적이 없을 경우 처리
            if (noRecordCell != null && noRecordCell.text().contains("기록이 없습니다")) {
                log.debug("전적 없음: %s(투수) vs %s(타자)%n", pitcherNm, hitterNm);
                return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(null, null, null, null);
            }

            // [13] 전적 데이터 파싱
            Element row = doc.selectFirst("table.tData.tt tbody tr");
            Elements td = row.select("td");
            Integer ab = parseIntSafe(td.get(2).text());  // 타석 수
            Integer h = parseIntSafe(td.get(3).text());  // 안타 수
            double avg = parseDoubleSafe(td.get(0).text());  // 타율
            double ops = parseDoubleSafe(td.get(13).text());  // OPS


            // [14] 결과 DTO로 반환
            return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(ab, h, avg, ops);

        } catch (Exception e) {
            log.error("전적 조회 실패: %s(투수) vs %s(타자)%n", pitcherNm, hitterNm);
            return null;
        } finally {
            // [15] 드라이버 종료
            driver.quit();
        }
    }


}
