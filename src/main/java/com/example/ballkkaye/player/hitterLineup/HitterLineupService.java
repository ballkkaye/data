package com.example.ballkkaye.player.hitterLineup;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.game.today.TodayGameRepository;
import com.example.ballkkaye.player.Player;
import com.example.ballkkaye.player.PlayerRepository;
import com.example.ballkkaye.player.startingPitcher.today.TodayStartingPitcherRepository;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.TeamRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.ballkkaye._core.util.Util.*;

@RequiredArgsConstructor
@Service
public class HitterLineupService {
    private final HitterLineupRepository hitterLineUpRepository;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final TodayGameRepository todayGameRepository;
    private final PlayerRepository playerRepository;
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;


    public void crawlHitterLineUpsByGameTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Game> todayGames = gameRepository.findByToday(); // 오늘 경기만 조회

        for (Game game : todayGames) {
            LocalDateTime gameTime = game.getGameTime().toLocalDateTime();

            // 경기 시작 1시간 전부터, 경기 시작 직전까지 허용
            if (!now.isBefore(gameTime.minusHours(1)) && now.isBefore(gameTime)) {
                crawlSingleGameLineup(game);
            }
        }
    }


    @Transactional
    public void crawlSingleGameLineup(Game game) {
        // [0] 이미 저장된 경우 생략
        if (hitterLineUpRepository.existsByGame(game)) {
            System.out.printf("⏩ 라인업 이미 존재 → 크롤링 생략 (gameId=%d)%n", game.getId());
            return;
        }

        // [1] 더블헤더 여부 확인
        List<Game> sameDayGames = gameRepository.findByGameDateAndTeamCombination(
                game.getGameTime().toLocalDateTime().toLocalDate(),
                game.getHomeTeam().getId(),
                game.getAwayTeam().getId());

        boolean isDoubleHeader = sameDayGames.size() > 1;
        if (isDoubleHeader) {
            System.out.printf(" 더블헤더 감지 → gameId=%d (%s vs %s)\n",
                    game.getId(),
                    game.getAwayTeam().getTeamName(),
                    game.getHomeTeam().getTeamName());
        }


        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.koreabaseball.com/Default.aspx?vote=true");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.game-cont")));

// [1] 우리가 가진 정보 준비
            String dateStr = game.getGameTime().toLocalDateTime().toLocalDate()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"));  // "20250704"
            String homeTeam = game.getHomeTeam().getTeamName().split(" ")[0]; // "두산"
            String awayTeam = game.getAwayTeam().getTeamName().split(" ")[0]; // "SSG"
            String stadiumName = simplifyStadiumName(game.getStadium().getStadiumName()); // "잠실"

// [2] 요소 필터링
            WebElement targetGameElement = driver.findElements(By.cssSelector("li.game-cont")).stream()
                    .filter(el ->
                            dateStr.equals(el.getAttribute("g_dt")) &&
                                    homeTeam.equals(el.getAttribute("home_nm")) &&
                                    awayTeam.equals(el.getAttribute("away_nm")) &&
                                    stadiumName.equals(el.getAttribute("s_nm"))
                    )
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format(
                            "게임 요소를 찾을 수 없습니다 → 날짜: %s, 홈팀: %s, 원정팀: %s, 구장: %s",
                            dateStr, homeTeam, awayTeam, stadiumName
                    )));

            // [1] 우천취소 여부 확인 (라인업 탭 클릭 전)
            String statusText = targetGameElement.findElement(By.cssSelector("p.staus")).getText().trim();
            if (statusText.contains("경기취소")) {
                System.out.printf("[%s vs %s] 우천취소 → 생략 (gameId=%d)\n",
                        game.getAwayTeam().getTeamName().split(" ")[0],
                        game.getHomeTeam().getTeamName().split(" ")[0],
                        game.getId());
                return;
            }

            // 경기 정보 준비
            Integer homeTeamId = game.getHomeTeam().getId();
            Integer awayTeamId = game.getAwayTeam().getId();


            // 선발투수 조회
            String homePitcher = todayStartingPitcherRepository.findByGameIdAndTeam(game.getId(), homeTeam).get(0);
            String awayPitcher = todayStartingPitcherRepository.findByGameIdAndTeam(game.getId(), awayTeam).get(0);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetGameElement.findElement(By.cssSelector("a#btnGame")));
            Thread.sleep(2000);

            WebElement lineupTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//ul[@id='tabGame']//a[contains(text(),'라인업')]")));
            lineupTab.click();
            Thread.sleep(2000);

            // [2] 라인업 발표 여부 확인
            boolean lineupAnnounced = !driver.findElements(By.cssSelector("#tableHitterB tbody tr")).isEmpty()
                    || !driver.findElements(By.cssSelector("#tableHitterT tbody tr")).isEmpty();

            if (!lineupAnnounced) {
                System.out.printf(" [%s vs %s] 라인업 아직 미발표 → 생략 (gameId=%d)\n",
                        game.getAwayTeam().getTeamName(),
                        game.getHomeTeam().getTeamName(),
                        game.getId());
                return;
            }

            List<HitterLineup> allEntities = new ArrayList<>();
            allEntities.addAll(extractLineups(driver, wait, game, awayTeamId, awayTeam, homeTeam, homePitcher, "#tableHitterB tbody tr"));
            allEntities.addAll(extractLineups(driver, wait, game, homeTeamId, homeTeam, awayTeam, awayPitcher, "#tableHitterT tbody tr"));

            if (!allEntities.isEmpty()) {
                hitterLineUpRepository.saveAll(allEntities);
                System.out.printf(" 라인업 저장 완료 (gameId=%s, 선수 %d명)\n", game.getId(), allEntities.size());
            }

        } catch (Exception e) {
            System.out.println("[ERROR] 타자 라인업 크롤링 실패 - gameId=" + game.getId());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private List<HitterLineup> extractLineups(WebDriver driver, WebDriverWait wait, Game game,
                                              Integer teamId, String hitterTeam, String opponentTeam, String opponentPitcher,
                                              String rowSelector) {
        List<HitterLineup> entities = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.cssSelector(rowSelector));

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.cssSelector("th, td"));
            if (cols.size() < 4) continue;

            String hitterName = cols.get(2).getText().trim();
            String position = cols.get(1).getText().trim();
            Double seasonAVG = parseSeasonAVG(cols);

            Player player = playerRepository.findByNameAndTeamId(hitterName, teamId)
                    .orElseThrow(() -> new RuntimeException("선수 없음"));
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("팀 없음"));

            if (!hitterLineUpRepository.existsByGameIdAndPlayerId(game.getId(), player.getId())) {
                HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO matchup =
                        crawlMatchup(driver, wait, opponentTeam, opponentPitcher, hitterTeam, hitterName);

                entities.add(HitterLineup.builder()
                        .game(game)
                        .team(team)
                        .player(player)
                        .hitterOrder(i + 1)
                        .position(position)
                        .seasonAvg(seasonAVG)
                        .ab(matchup != null ? matchup.getAb() : null)
                        .h(matchup != null ? matchup.getH() : null)
                        .avg(matchup != null ? matchup.getAvg() : null)
                        .ops(matchup != null ? matchup.getOps() : null)
                        .build());
            }
        }

        return entities;
    }


    // 투수와 타자 간의 맞대결 전적을 크롤링하여 DTO로 반환
    // 내부 전용 메서드
    private HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO crawlMatchup(
            WebDriver driver, WebDriverWait wait,
            String pitcherTeamNm, String pitcherNm,
            String hitterTeamNm, String hitterNm) {

        // 셀레니움 드라이버 및 대기 설정
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new");
        opts.addArguments("--disable-gpu");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        opts.addArguments("--disable-extensions");


        try {
            // 맞대결 전적 페이지 접속
            driver.get("https://www.koreabaseball.com/Record/Etc/HitVsPit.aspx");

            // 투수팀
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlPitcherTeam")))), pitcherTeamNm);

            // 투수
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")))), pitcherNm);

            // 타자팀
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterTeam")))), hitterTeamNm);

            // 타자
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterPlayer")))), hitterNm);

            // 5. [검색] 버튼 클릭
            driver.findElement(By.id("cphContents_cphContents_cphContents_btnSearch")).click();

            // 6. 결과 테이블이 로딩될 때까지 대기
            wait.until(d -> Jsoup.parse(d.getPageSource())
                    .selectFirst("table.tData.tt tbody tr") != null);

            // 7. HTML 파싱하여 첫 번째 row 추출
            Document doc = Jsoup.parse(driver.getPageSource());
            Element row = doc.selectFirst("table.tData.tt tbody tr");

            // 8. "기록이 없습니다" 메시지 확인 (대결 전적 없음)
            if (row.select("td[colspan=15]").text().contains("기록이 없습니다")) {
                return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(null, null, null, null);
            }

            // 9. 데이터 추출: 타율(avg), 타석수(ab), 안타수(h), OPS
            Elements td = row.select("td");
            Integer ab = parseIntSafe(td.get(2).text());
            Integer h = parseIntSafe(td.get(3).text());
            double avg = parseDoubleSafe(td.get(0).text());
            double ops = parseDoubleSafe(td.get(13).text());

            // 10. DTO로 반환
            return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(ab, h, avg, ops);

        } catch (Exception e) {
            // 예외 발생 시 로그 출력 후 null 반환
            System.out.println(" 전적 조회 실패: " + hitterNm + " vs " + pitcherNm);
            return null;
        } finally {
            // 브라우저 자원 해제
            driver.quit();
        }
    }

    public static String simplifyStadiumName(String fullName) {
        return switch (fullName.trim()) {
            case "잠실야구장" -> "잠실";
            case "고척스카이돔" -> "고척";
            case "수원 KT위즈파크" -> "수원";
            case "인천 SSG 랜더스필드" -> "문학";
            case "광주-기아 챔피언스필드" -> "광주";
            case "대구 삼성라이온즈파크" -> "대구";
            case "부산 사직야구장" -> "부산";
            case "대전 한화생명이글스파크" -> "대전";
            case "창원 NC파크" -> "창원";
            case "청주 야구장" -> "청주";
            case "울산 문수야구장" -> "울산";
            case "포항 야구장" -> "포항";
            case "군산 월명야구장" -> "군산";
            default -> fullName; // fallback
        };
    }
}
