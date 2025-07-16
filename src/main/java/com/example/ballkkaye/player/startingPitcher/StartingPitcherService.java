package com.example.ballkkaye.player.startingPitcher;

import com.example.ballkkaye._core.error.ex.Exception404;
import com.example.ballkkaye._core.util.UtilMapper;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.player.Player;
import com.example.ballkkaye.player.PlayerRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

import static com.example.ballkkaye._core.util.Util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class StartingPitcherService {
    private final StartingPitcherRepository startingPitcherRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;


    // 선발투수 크롤링 + DB 저장
    @Transactional
    public void startingPitchers() {
        WebDriverManager.chromedriver().setup();
        List<StartingPitcher> entities = new ArrayList<>();
        WebDriver driver = null;

        try {
            // [1] 대상 날짜 지정
            String targetDate = LocalDate.now()
//                    .plusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            log.debug("크롤링 대상 날짜: {}", targetDate);

            // [2] WebDriver 옵션 설정 및 페이지 접속
            ChromeOptions opts = new ChromeOptions();
            opts.addArguments("--headless=new");
            opts.addArguments("--disable-gpu"); // GPU 비활성화
            opts.addArguments("--no-sandbox");
            opts.addArguments("--disable-dev-shm-usage"); // 메모리 공유 공간 문제 방지
            opts.addArguments("--disable-extensions");     // 확장기능 제거

            driver = new ChromeDriver(opts);
            driver.get("https://www.koreabaseball.com/Schedule/GameCenter/Main.aspx?gameDate=" + targetDate);


            // [3] 경기 리스트 로딩 대기
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.game-cont")));

            // [4] 경기별 데이터 수집
            for (WebElement gameEl : driver.findElements(By.cssSelector("li.game-cont"))) {

                /* ─ 경기·팀·투수 식별자 추출 ─ */
                String gameIdRaw = gameEl.getAttribute("g_id");
                String gameDate = gameIdRaw.substring(0, 8); // yyyyMMdd
                String awayTeamId = gameEl.getAttribute("away_id");
                String homeTeamId = gameEl.getAttribute("home_id");
                String awayPitId = gameEl.getAttribute("away_p_id");
                String homePitId = gameEl.getAttribute("home_p_id");

                // 선발 투수 정보 없을 경우 건너뜀
                if (awayPitId == null || homePitId == null ||
                        awayPitId.isBlank() || homePitId.isBlank()) {
                    log.debug("선발 투수 정보 누락으로 경기 {} 스킵", gameIdRaw);
                    continue;
                }

                // [5] 선발투수 분석 API 호출
                JsonObject api = callPitcherAnalysisApi(awayTeamId, awayPitId, homeTeamId, homePitId);
                if (api == null) {
                    log.debug("투수 분석 API 결과 없음으로 경기 {} 스킵", gameIdRaw);
                    continue;
                }

                // [6] 내부 팀 ID 매핑
                Integer homeTeamDbId = UtilMapper.getTeamIdByCode(homeTeamId);
                Integer awayTeamDbId = UtilMapper.getTeamIdByCode(awayTeamId);

                if (homeTeamDbId == null || awayTeamDbId == null) {
                    String message = String.format("알 수 없는 팀 코드: home=%s, away=%s", homeTeamId, awayTeamId);
                    log.error(message);
                    continue;
                }

                // [7] Game 조회
                Game game = gameRepository.findGameByDateAndTeams(gameDate, homeTeamDbId, awayTeamDbId)
                        .orElseThrow(() -> {
                            log.warn("Game 테이블에서 날짜={}, 홈팀={}, 어웨이팀={}에 대한 경기 없음", gameDate, homeTeamDbId, awayTeamDbId);
                            return new Exception404("해당 자원을 찾을 수 없습니다.");
                        });


                // [8] 팀별 선발투수 처리
                for (String teamType : List.of("away", "home")) {
                    int idx = teamType.equals("away") ? 0 : 1;
                    Integer kboPlayerId = Integer.parseInt(teamType.equals("away") ? awayPitId : homePitId);

                    Player player = playerRepository.findByKboPlayerId(kboPlayerId)
                            .orElseThrow(() -> {
                                log.warn("Player 테이블에 KBO playerId {} 없음", kboPlayerId);
                                return new Exception404("해당 자원을 찾을 수 없습니다.");
                            });
                    log.debug("Player 조회 성공: playerId={}, KBO playerId={}", player.getId(), kboPlayerId);

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
                            .ERA(parseNullableDouble(row.get(1).getAsJsonObject().get("Text").getAsString()))
                            .gameCount(safeParseInt(row.get(3).getAsJsonObject().get("Text").getAsString()))
                            .result(resultS)
                            .QS(safeParseInt(row.get(5).getAsJsonObject().get("Text").getAsString()))
                            .WHIP(parseNullableDouble(row.get(6).getAsJsonObject().get("Text").getAsString()))
                            .build();

                    entities.add(entity);
                    log.debug("StartingPitcher 추가됨: gameId={}, playerId={}, team={}", game.getId(), player.getId(), teamType);
                }
            }

        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("선발투수 크롤링 중 예외 발생", e);
        } finally {
            if (driver != null) driver.quit();
        }

        // [9] 저장 처리
        if (!entities.isEmpty()) {
            startingPitcherRepository.saveAll(entities);
            log.info("{}개의 선발투수 데이터 저장 완료", entities.size());
        } else {
            log.info("저장할 선발투수 데이터 없음 (entities 비어있음)");
        }
    }


    // 내부 메서드
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
            log.error("투수 분석 API 호출 실패 - awayTeamId={}, awayPitId={}, homeTeamId={}, homePitId={}",
                    awayTeamId, awayPitId, homeTeamId, homePitId, e);
            return null;
        }
    }

}