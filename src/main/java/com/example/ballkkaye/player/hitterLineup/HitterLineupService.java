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
import java.util.Optional;

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


    @Transactional
    public void crawlHitterLineUpsByGameTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Game> todayGames = gameRepository.findByToday(); // 오늘 경기만 조회

        for (Game game : todayGames) {
            LocalDateTime gameTime = game.getGameTime().toLocalDateTime();

            // 경기 시작 1시간 전부터, 경기 시작 직전까지 허용
            if (!now.isBefore(gameTime.minusHours(1)) && now.isBefore(gameTime)) {
                crawlAndSaveSingleGameLineup(game);
            }
        }
    }

    @Transactional
    public void crawlAndSaveSingleGameLineup(Game game) {
        List<HitterLineup> entities = crawlSingleGameLineup(game);
        if (!entities.isEmpty()) {
            saveLineups(entities);
        }
    }

    public List<HitterLineup> crawlSingleGameLineup(Game game) {
        List<HitterLineup> allEntities = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.koreabaseball.com/Default.aspx?vote=true");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.game-cont")));

            String dateStr = game.getGameTime().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String homeTeam = game.getHomeTeam().getTeamName().split(" ")[0];
            String awayTeam = game.getAwayTeam().getTeamName().split(" ")[0];
            String stadiumName = simplifyStadiumName(game.getStadium().getStadiumName());

            WebElement targetGameElement = driver.findElements(By.cssSelector("li.game-cont")).stream()
                    .filter(el ->
                            dateStr.equals(el.getAttribute("g_dt")) &&
                                    homeTeam.equals(el.getAttribute("home_nm")) &&
                                    awayTeam.equals(el.getAttribute("away_nm")) &&
                                    stadiumName.equals(el.getAttribute("s_nm"))
                    )
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("게임 요소를 찾을 수 없습니다"));

            String statusText = targetGameElement.findElement(By.cssSelector("p.staus")).getText().trim();
            if (statusText.contains("경기취소")) return allEntities;

            Integer homeTeamId = game.getHomeTeam().getId();
            Integer awayTeamId = game.getAwayTeam().getId();
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
            }

            if (!driver.findElements(By.cssSelector("#tableHitterB tbody tr")).isEmpty()) {
                allEntities.addAll(extractLineups(driver, wait, game, homeTeamId, homeTeam, awayTeam, awayPitcher, "#tableHitterB tbody tr"));
            }
            if (!driver.findElements(By.cssSelector("#tableHitterT tbody tr")).isEmpty()) {
                allEntities.addAll(extractLineups(driver, wait, game, awayTeamId, awayTeam, homeTeam, homePitcher, "#tableHitterT tbody tr"));
            }

        } catch (Exception e) {
            System.out.println("[ERROR] 타자 라인업 크롤링 실패 - gameId=" + game.getId());
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return allEntities;
    }

    private List<HitterLineup> extractLineups(WebDriver driver, WebDriverWait wait, Game game,
                                              Integer teamId, String hitterTeam, String opponentTeam, String opponentPitcher,
                                              String rowSelector) {
        List<HitterLineup> entities = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.cssSelector(rowSelector));

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.cssSelector("th, td"));
            if (cols.size() < 4) continue;

            String hitterName = cols.get(2).getText().replaceAll("\\s+", "").trim(); // 공백 전부 제거
            String position = cols.get(1).getText().trim();
            Double seasonAVG = parseSeasonAVG(cols);

            Player player = playerRepository.findByNameAndTeamId(hitterName, teamId)
                    .orElseThrow(() -> new RuntimeException("선수 없음"));
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("팀 없음"));

            HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO matchup =
                    crawlMatchup(opponentTeam, opponentPitcher, hitterTeam, hitterName);

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

        return entities;
    }

    @Transactional
    public void saveLineups(List<HitterLineup> hitterLineups) {
        hitterLineUpRepository.saveAll(hitterLineups);
    }

    private HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO crawlMatchup(
            String pitcherTeamNm, String pitcherNm, String hitterTeamNm, String hitterNm) {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(opts);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.koreabaseball.com/Record/Etc/HitVsPit.aspx");
            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlPitcherTeam")))), pitcherTeamNm);

            // 선수 목록 로딩 대기
            wait.until(d -> {
                Select s = new Select(d.findElement(By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")));
                return s.getOptions().size() > 1;
            });

// 디버그 출력
            Select pitcherSelect = new Select(driver.findElement(By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")));
            for (WebElement option : pitcherSelect.getOptions()) {
                System.out.println("[DEBUG] 투수 옵션: " + option.getText());
                System.out.println("[DEBUG] 선수 옵션: '" + option.getText() + "'");
            }

            selectByContainingText(new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlPitcherPlayer")))), pitcherNm);

            Select hitterTeamSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterTeam"))));
            selectByContainingText(hitterTeamSelect, hitterTeamNm);
            System.out.println("[DEBUG] 타자팀 선택 완료: " + hitterTeamNm);


// [2] 타자 목록 로딩될 때까지 대기 (옵션 2개 이상 나올 때까지 → '타자' + 선수명 1명 이상)
            wait.until(driver1 -> {
                Select s = new Select(driver1.findElement(
                        By.id("cphContents_cphContents_cphContents_ddlHitterPlayer")));
                return s.getOptions().stream()
                        .anyMatch(opt -> opt.getText().contains(hitterNm));
            });


// [3] 타자 옵션 확인
            Select hitterSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("cphContents_cphContents_cphContents_ddlHitterPlayer"))));
            for (WebElement option : hitterSelect.getOptions()) {
                System.out.println("[DEBUG] 타자 옵션: '" + option.getText() + "'");
            }
            System.out.println("[DEBUG] 타자 키워드: " + hitterNm);

// [4] 타자 선택
            selectByContainingText(hitterSelect, hitterNm);
            driver.findElement(By.id("cphContents_cphContents_cphContents_btnSearch")).click();

            wait.until(d -> Jsoup.parse(d.getPageSource())
                    .selectFirst("table.tData.tt tbody tr") != null);

            Document doc = Jsoup.parse(driver.getPageSource());
            Element noRecordCell = doc.selectFirst("table.tData.tt tbody tr td[colspan=15]");
            if (noRecordCell != null && noRecordCell.text().contains("기록이 없습니다")) {
                System.out.printf("전적 없음: %s(투수) vs %s(타자)%n", pitcherNm, hitterNm);
                return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(null, null, null, null);
            }

            Element row = doc.selectFirst("table.tData.tt tbody tr");
            Elements td = row.select("td");
            Integer ab = parseIntSafe(td.get(2).text());
            Integer h = parseIntSafe(td.get(3).text());
            double avg = parseDoubleSafe(td.get(0).text());
            double ops = parseDoubleSafe(td.get(13).text());

            return new HitterLineupRequest.HitterSaveDTO.HitterInfo.MachUpStatusDTO(ab, h, avg, ops);

        } catch (Exception e) {
            System.out.printf(" 전적 조회 실패: %s(투수) vs %s(타자)%n", pitcherNm, hitterNm);
            return null;
        } finally {
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
