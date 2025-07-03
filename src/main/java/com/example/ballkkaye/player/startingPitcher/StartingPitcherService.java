package com.example.ballkkaye.player.startingPitcher;

import com.example.ballkkaye._core.util.UtilMapper;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.player.Player;
import com.example.ballkkaye.player.PlayerRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StartingPitcherService {
    private final StartingPitcherRepository startingPitcherRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    /**
     * 선발투수 크롤링 + DB 저장 (로그 → 콘솔 출력)
     */
    @Transactional
    public void startingPitchers() {
        WebDriverManager.chromedriver().setup();
        List<StartingPitcher> entities = new ArrayList<>();
        WebDriver driver = null;

        try {
            /* 1) GameCenter 접속 (내일 날짜) */
            String targetDate = LocalDate.now()
                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            System.out.println("[DEBUG] 크롤링 대상 날짜 (targetDate): " + targetDate); // 디버깅: 크롤링 대상 날짜 출력

            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--headless=new");
            opts.addArguments("--disable-gpu"); // GPU 비활성화
            opts.addArguments("--no-sandbox");
            opts.addArguments("--disable-dev-shm-usage"); // 메모리 공유 공간 문제 방지
            opts.addArguments("--disable-extensions");     // 확장기능 제거

            driver = new ChromeDriver(opts);
            driver.get("https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx?gameDate=" + targetDate);

            /* 경기 리스트(li.game-cont) 로딩 대기 (최대 10초) */
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.game-cont")));

            /* 2) 경기 목록 순회 */
            for (WebElement gameEl : driver.findElements(By.cssSelector("li.game-cont"))) {

                /* ─ 경기·팀·투수 식별자 추출 ─ */
                String gameIdRaw = gameEl.getAttribute("g_id");
                String gameDate = gameIdRaw.substring(0, 8); // yyyyMMdd
                String awayTeamId = gameEl.getAttribute("away_id");
                String homeTeamId = gameEl.getAttribute("home_id");
                String awayPitId = gameEl.getAttribute("away_p_id");
                String homePitId = gameEl.getAttribute("home_p_id");

                // 선발투수 미확정 시 skip
                if (awayPitId == null || homePitId == null ||
                        awayPitId.isBlank() || homePitId.isBlank()) {
                    System.out.println("[DEBUG] 선발 투수 미확정 또는 정보 누락으로 게임 " + gameIdRaw + " 스킵"); // 디버깅: 스킵 이유 출력
                    continue;
                }

                /* 3) 내부 API 호출 → 선발투수 시즌 스탯(JSON) */
                JsonObject api = callPitcherAnalysisApi(awayTeamId, awayPitId, homeTeamId, homePitId);
                if (api == null) {
                    System.out.println("[DEBUG] 투수 분석 API 호출 실패 또는 결과 없음으로 게임 " + gameIdRaw + " 스킵"); // 디버깅: 스킵 이유 출력
                    continue;
                }

                Integer homeTeamDbId = UtilMapper.getTeamIdByCode(homeTeamId);
                Integer awayTeamDbId = UtilMapper.getTeamIdByCode(awayTeamId);

                if (homeTeamDbId == null || awayTeamDbId == null) {
                    System.out.println("[ERROR] 알 수 없는 팀 코드: " + homeTeamDbId + " 또는 " + awayTeamDbId);
                    continue;
                }

                Game game = gameRepository.findGameByDateAndTeams(gameDate, homeTeamDbId, awayTeamDbId);
                if (game == null) {
                    System.out.println("[WARN] Game 테이블에서 날짜=" + gameDate + ", 홈팀=" + homeTeamDbId + ", 어웨이팀=" + awayTeamDbId + " 로 경기 ID 찾을 수 없음");
                    continue;
                }

                for (String teamType : List.of("away", "home")) {
                    int idx = teamType.equals("away") ? 0 : 1;
                    Integer kboPlayerId = Integer.parseInt(teamType.equals("away") ? awayPitId : homePitId);

                    Player player = playerRepository.findByKboPlayerId(kboPlayerId);
                    if (player == null) {
                        System.out.println("[WARN] Player 테이블에 KBO playerId " + kboPlayerId + " 없음");
                        continue;
                    }
                    System.out.println("[DEBUG] Player 테이블에서 찾은 playerId: " + player.getId() + " (KBO playerId: " + kboPlayerId + ")");

                    JsonArray row = api.getAsJsonArray("rows")
                            .get(idx).getAsJsonObject()
                            .getAsJsonArray("row");

                    Document html = Jsoup.parse(row.get(0).getAsJsonObject().get("Text").getAsString());
                    String imgUrl = parseImgUrl(html);
                    String resultS = parseResultString(html);

                    StartingPitcher entity = StartingPitcher.builder()
                            .game(game)
                            .player(player)
                            .profileUrl(imgUrl)
                            .ERA(safeParseDouble(row.get(1).getAsJsonObject().get("Text").getAsString()))
                            .gameCount(safeParseInt(row.get(3).getAsJsonObject().get("Text").getAsString()))
                            .result(resultS)
                            .QS(safeParseInt(row.get(5).getAsJsonObject().get("Text").getAsString()))
                            .WHIP(safeParseDouble(row.get(6).getAsJsonObject().get("Text").getAsString()))
                            .build();

                    entities.add(entity);
                    System.out.println("[DEBUG] StartingPitcher 엔티티 추가됨: gameId=" + game.getId() + ", playerId=" + player.getId() + " (Team: " + teamType + ")");
                }
            }

        } catch (Exception e) {
            System.out.println("[ERROR] 선발투수 크롤링 실패");
            e.printStackTrace();
        } finally {
            if (driver != null) driver.quit();
        }

        if (!entities.isEmpty()) {
            startingPitcherRepository.saveAll(entities);
            System.out.println("[INFO] " + entities.size() + " 개의 선발투수 데이터 저장 완료");
        } else {
            System.out.println("저장할 데이터가 없습니다");
            System.out.println("[INFO] entities 리스트가 비어있음. 크롤링된 데이터가 없거나 모든 항목이 스킵됨.");
        }
    }


    /*------------------ TODO: Util함수로 빼기 ------------------*/
    // KBO 내부 API 호출 : 투수 시즌 스탯 JSON 얻기
    // Jsoup을 사용하여 HTTP POST 요청을 보내고, 그 응답을 JSON 형식으로 파싱(역직렬화) 하여 JsonObject로 반환
    private JsonObject callPitcherAnalysisApi(String awayTeamId, String awayPitId, String homeTeamId, String homePitId) {
        try {
            Connection.Response response = Jsoup.connect("https://www.koreabaseball.com/ws/Schedule.asmx/GetPitcherRecordAnalysis")
                    .method(Connection.Method.POST)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .ignoreContentType(true)
                    .data("leId", "1")
                    .data("srId", "0")
                    .data("seasonId", "2025")
                    .data("awayTeamId", awayTeamId)
                    .data("awayPitId", awayPitId)
                    .data("homeTeamId", homeTeamId)
                    .data("homePitId", homePitId)
                    .data("groupSc", "SEASON")
                    .execute();

            return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (IOException e) {
            System.out.println("[ERROR] API 호출 실패: " + e.getMessage()); // 디버깅: 실패 원인 메시지 출력
            // e.printStackTrace(); // 필요한 경우 주석 해제하여 스택 트레이스 확인
            return null;
        }
    }

    // HTML <span class="record">...</span> 에서 시즌 전적 문자열 추출
    // ex) "시즌 10승 3패 VS 상대 ..." → "10승 3패"
    private String parseResultString(Document doc) {
        Element recordEl = doc.selectFirst(".record");
        if (recordEl == null) return "없음";
        String txt = recordEl.text().replace("시즌 ", "").trim();
        return txt.isEmpty() ? "없음" : txt.contains("VS") ? txt.split("VS")[0].trim() : txt;
    }

    // HTML 내 두 번째 <img> 태그(src) → 프로필 이미지 URL 반환
    // '//' 로 시작하면 "https:" 접두어 추가
    private String parseImgUrl(Document doc) {
        Elements imgs = doc.select("img");
        if (imgs.size() >= 2) {
            String path = imgs.get(1).attr("src");
            return path.startsWith("http") ? path : "https:" + path;
        }
        return "";
    }

    private Double safeParseDouble(String s) {
        if (s == null || s.trim().equals("-")) return null;
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer safeParseInt(String s) {
        if (s == null || s.trim().equals("-")) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}