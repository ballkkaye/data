package com.example.ballkkaye.game;

import com.example.ballkkaye._core.util.UtilMapper;
import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.stadium.StadiumRepository;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.TeamRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final StadiumRepository stadiumRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void save() {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--headless=new");

            driver = new ChromeDriver(options);
            driver.get("https://www.koreabaseball.com/Schedule/Schedule.aspx#");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ddlYear")));
            WebElement ddlMonthElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlMonth")));
            Select ddlMonthSelect = new Select(ddlMonthElement);

            for (WebElement monthOption : ddlMonthSelect.getOptions()) {
                String monthValue = monthOption.getAttribute("value").trim();
                int monthNum = Integer.parseInt(monthValue);
                if (monthNum < 3 || monthNum > 8) continue;

                if (!ddlMonthSelect.getFirstSelectedOption().getAttribute("value").equals(monthValue)) {
                    ddlMonthSelect.selectByValue(monthValue);
                    try {
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tblScheduleList tbody td")));
                    } catch (TimeoutException e) {
                        continue;
                    }
                }

                List<GameData> parsedGameDataList = extractGameData(driver, wait);

                for (GameData gameData : parsedGameDataList) {
                    if (gameData.getStadiumId() == null || gameData.getHomeTeamId() == null ||
                            gameData.getAwayTeamId() == null || gameData.getGameTime() == null ||
                            gameData.getBroadcastChannel() == null) continue;

                    if (gameRepository.existsByGameCode(gameData.getGameCode())) continue;

                    GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);
                    Stadium stadium = stadiumRepository.findById(saveDTO.getStadiumId());
                    Team homeTeam = teamRepository.findById(saveDTO.getHomeTeamId());
                    Team awayTeam = teamRepository.findById(saveDTO.getAwayTeamId());

                    Game game = Game.builder()
                            .gameCode(saveDTO.getGameCode())
                            .stadium(stadium)
                            .homeTeam(homeTeam)
                            .awayTeam(awayTeam)
                            .gameTime(saveDTO.getGameTime())
                            .gameStatus(saveDTO.getGameStatus())
                            .homeResultScore(saveDTO.getHomeResultScore())
                            .awayResultScore(saveDTO.getAwayResultScore())
                            .broadcastChannel(saveDTO.getBroadcastChannel())
                            .homePredictionScore(saveDTO.getHomePredictionScore())
                            .awayPredictionScore(saveDTO.getAwayPredictionScore())
                            .totalPredictionScore(saveDTO.getTotalPredictionScore())
                            .homeWinPer(saveDTO.getHomeWinPer())
                            .awayWinPer(saveDTO.getAwayWinPer())
                            .build();

                    gameRepository.save(game);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                try {
                    Thread.sleep(3000);
                    driver.quit();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private List<GameData> extractGameData(WebDriver driver, WebDriverWait wait) {
        List<GameData> result = new ArrayList<>();
        WebElement tbody = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tblScheduleList"))),
                By.tagName("tbody")));

        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        String currentDate = "";
        int currentYear = LocalDate.now().getYear();

        for (WebElement row : rows) {
            String gameCode = null;
            try {
                WebElement link = row.findElement(By.cssSelector("a[href*='gameId=']"));
                String href = link.getAttribute("href");

                // gameId가 존재할 경우 추출
                Matcher m = Pattern.compile("gameId=([^&]+)").matcher(href);
                if (m.find()) {
                    gameCode = m.group(1); // ex: 20250701SSOB0
                }
            } catch (Exception e) {
                // gameId가 없는 경기 (예: 아직 하이라이트 없음)
                gameCode = null;
            }


            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) continue;
            if (cells.size() == 1 && cells.get(0).getText().contains("등록된 일정이 없습니다.")) continue;

            String rawDate = "", rawTime = "", rawGame = "", rawTv = "", rawStadium = "", rawRemarks = "";
            String firstText = cells.get(0).getText().trim();

            if (firstText.matches("^\\d{2}\\.\\d{2}\\(.*\\)$")) {
                currentDate = firstText;
                rawDate = currentDate;
                if (cells.size() > 1) rawTime = cells.get(1).getText().trim();
                if (cells.size() > 2) rawGame = cells.get(2).getText().trim();
                if (cells.size() > 5) rawTv = extractTvChannel(cells.get(5));
                if (cells.size() > 7) rawStadium = cells.get(7).getText().trim();
                if (cells.size() > 8) rawRemarks = cells.get(8).getText().trim();
            } else {
                rawDate = currentDate;
                rawTime = firstText;
                if (cells.size() > 1) rawGame = cells.get(1).getText().trim();
                if (cells.size() > 4) rawTv = extractTvChannel(cells.get(4));
                if (cells.size() > 6) rawStadium = cells.get(6).getText().trim();
                if (cells.size() > 7) rawRemarks = cells.get(7).getText().trim();
            }

            Timestamp gameTime = null;
            try {
                String dateTime = String.format("%d.%s %s", currentYear,
                        rawDate.replaceAll("\\(.*\\)", ""), rawTime);
                gameTime = Timestamp.valueOf(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
            } catch (DateTimeParseException ignored) {
            }

            Integer homeScore = null, awayScore = null, stadiumId = UtilMapper.getStadiumId(rawStadium);
            Integer homeTeamId = null, awayTeamId = null;
            BroadcastChannel channel = BroadcastChannel.fromString(rawTv);

            Pattern compact = Pattern.compile("([가-힣A-Z]+)(\\d+)vs(\\d+)([가-힣A-Z]+)");
            Pattern spaced = Pattern.compile("([가-힣A-Z]+)vs([가-힣A-Z]+)\\s*(\\d+)[^\\d]?(\\d+)");
            Pattern plain = Pattern.compile("([가-힣A-Z]+)vs([가-힣A-Z]+)");

            if (compact.matcher(rawGame).find()) {
                Matcher m = compact.matcher(rawGame);
                m.find();
                awayTeamId = UtilMapper.getTeamId(m.group(1));
                awayScore = Integer.parseInt(m.group(2));
                homeScore = Integer.parseInt(m.group(3));
                homeTeamId = UtilMapper.getTeamId(m.group(4));
            } else if (spaced.matcher(rawGame).find()) {
                Matcher m = spaced.matcher(rawGame);
                m.find();
                awayTeamId = UtilMapper.getTeamId(m.group(1));
                homeTeamId = UtilMapper.getTeamId(m.group(2));
                awayScore = Integer.parseInt(m.group(3));
                homeScore = Integer.parseInt(m.group(4));
            } else if (plain.matcher(rawGame).find()) {
                Matcher m = plain.matcher(rawGame);
                m.find();
                awayTeamId = UtilMapper.getTeamId(m.group(1));
                homeTeamId = UtilMapper.getTeamId(m.group(2));
            }

            GameStatus gameStatus;
            boolean hasLiveText = rawTv.contains("중계");

            if (rawRemarks.contains("우천") || rawRemarks.contains("기타")) {
                gameStatus = GameStatus.CANCELLED;
            } else if (homeScore != null && awayScore != null) {
                gameStatus = GameStatus.COMPLETED;
            } else if (hasLiveText && gameTime != null && gameTime.toLocalDateTime().isBefore(LocalDateTime.now())) {
                gameStatus = GameStatus.IN_PROGRESS;
            } else if (!hasLiveText && gameTime != null && gameTime.toLocalDateTime().isBefore(LocalDateTime.now())) {
                gameStatus = GameStatus.CANCELLED;
            } else {
                gameStatus = GameStatus.SCHEDULED;
            }

            result.add(new GameData(gameCode, gameTime, gameStatus, homeScore, awayScore, channel,
                    stadiumId, homeTeamId, awayTeamId));
        }
        return result;
    }

    private String extractTvChannel(WebElement tvCell) {
        List<WebElement> imgs = tvCell.findElements(By.tagName("img"));
        if (!imgs.isEmpty()) {
            return imgs.get(0).getAttribute("alt").trim();
        } else {
            String text = tvCell.getText().trim();
            return text.split("[\\n\\s,]+")[0];
        }
    }

    @Getter
    @NoArgsConstructor
    static class GameData {
        private String gameCode;
        private Timestamp gameTime;
        private GameStatus gameStatus;
        private Integer homeResultScore;
        private Integer awayResultScore;
        private BroadcastChannel broadcastChannel;
        private Integer stadiumId;
        private Integer homeTeamId;
        private Integer awayTeamId;

        public GameData(String gameCode, Timestamp gameTime, GameStatus gameStatus,
                        Integer homeResultScore, Integer awayResultScore,
                        BroadcastChannel broadcastChannel, Integer stadiumId,
                        Integer homeTeamId, Integer awayTeamId) {
            this.gameCode = gameCode;
            this.gameTime = gameTime;
            this.gameStatus = gameStatus;
            this.homeResultScore = homeResultScore;
            this.awayResultScore = awayResultScore;
            this.broadcastChannel = broadcastChannel;
            this.stadiumId = stadiumId;
            this.homeTeamId = homeTeamId;
            this.awayTeamId = awayTeamId;
        }
    }

    // GameService.java 내에 추가할 메소드 (기존 save() 메소드 아래에 추가하세요)
    @Transactional
    public void updateTodayGames() {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--headless=new"); // 필요에 따라 --headless=new 옵션을 제거하여 브라우저 창을 볼 수 있습니다.

            driver = new ChromeDriver(options);
            driver.get("https://www.koreabaseball.com/Schedule/Schedule.aspx#");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ddlYear")));
            WebElement ddlMonthElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlMonth")));
            Select ddlMonthSelect = new Select(ddlMonthElement);

            // 현재 날짜를 기준으로 월을 선택합니다.
            // 2025년 7월 4일 기준, 7월의 데이터만 가져오게 됩니다.
            LocalDate today = LocalDate.now();
            String currentMonthValue = String.valueOf(today.getMonthValue());

            // 해당 월로 드롭다운을 선택합니다.
            ddlMonthSelect.selectByValue(currentMonthValue);
            try {
                // 월 변경 후 데이터 로딩 대기
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tblScheduleList tbody td")));
            } catch (TimeoutException e) {
                System.out.println("현재 월의 경기 일정이 없거나 로딩에 실패했습니다: " + currentMonthValue + "월");
                return; // 현재 월의 데이터가 없으면 메소드 종료
            }

            List<GameData> parsedGameDataList = extractGameData(driver, wait); // 기존 스크래핑 로직 재사용

            for (GameData gameData : parsedGameDataList) {
                // 1. 오늘 날짜의 경기인지 확인
                // gameData.getGameTime()이 null이거나 오늘 날짜가 아니면 업데이트 대상이 아니므로 건너뜁니다.
                if (gameData.getGameTime() == null || !gameData.getGameTime().toLocalDateTime().toLocalDate().isEqual(today)) {
                    System.out.println("오늘 날짜 경기가 아님. 건너뛰기: " + gameData.getGameTime());
                    continue;
                }

                // 2. 필수 데이터 누락 확인 (기존 save() 로직 유지)
                if (gameData.getStadiumId() == null || gameData.getHomeTeamId() == null ||
                        gameData.getAwayTeamId() == null || gameData.getGameTime() == null ||
                        gameData.getBroadcastChannel() == null) {
                    System.out.println("오늘 경기이나 필수 데이터 누락으로 건너뛰기: " + gameData);
                    continue;
                }

                // 3. gameCode가 없거나 비어있고, 상태가 취소/완료된 경기는 건너뛰기 (기존 필터 유지)
                // 이는 불완전한 확정 경기가 업데이트되는 것을 방지합니다.
                if ((gameData.getGameCode() == null || gameData.getGameCode().trim().isEmpty()) &&
                        (gameData.getGameStatus() == GameStatus.CANCELLED || gameData.getGameStatus() == GameStatus.COMPLETED)) {
                    System.out.println("오늘 경기이나 gameCode가 없으며 취소/완료 상태. 건너뛰기: " + gameData.getGameTime() + " | " + gameData.getGameStatus());
                    continue;
                }

                // 4. gameCode가 있는 경우에만 DB에서 기존 경기 찾아 업데이트
                if (gameData.getGameCode() != null) {
                    // gameCode로 기존 경기 엔티티를 찾습니다.
                    // 고객님의 GameRepository에 findByGameCode(String gameCode) 메소드가 구현되어 있어야 합니다.
                    Game existingGame = gameRepository.findByGameCode(gameData.getGameCode());

                    if (existingGame != null) {
                        // 5. 기존 경기가 존재하면 요청하신 컬럼만 업데이트
                        System.out.println("오늘 경기 업데이트: " + existingGame.getGameCode());
                        existingGame.setGameStatus(gameData.getGameStatus());       // 경기 상태 업데이트
                        existingGame.setHomeResultScore(gameData.getHomeResultScore()); // 홈팀 득점 업데이트
                        existingGame.setAwayResultScore(gameData.getAwayResultScore()); // 어웨이팀 득점 업데이트

                        // 예측 관련 필드 (homePredictionScore, awayPredictionScore, totalPredictionScore, homeWinPer, awayWinPer)는
                        // 스크래핑 시점에서는 변경되지 않지만, `GameRequest.SaveDTO.fromGameData`에서 0.0 또는 50.0으로 초기값을 설정하므로,
                        // 일관성을 위해 이 값을 다시 설정할 수 있습니다.
                        // 현재 KBO 웹사이트에서 이 값들을 얻을 수 없으므로, 업데이트 시점에 이 값들은 스크래핑된 값(초기값)으로 덮어쓰여집니다.
                        GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);
                        existingGame.setHomePredictionScore(saveDTO.getHomePredictionScore());
                        existingGame.setAwayPredictionScore(saveDTO.getAwayPredictionScore());
                        existingGame.setTotalPredictionScore(saveDTO.getTotalPredictionScore());
                        existingGame.setHomeWinPer(saveDTO.getHomeWinPer());
                        existingGame.setAwayWinPer(saveDTO.getAwayWinPer());

                        // 중계 채널도 변경될 수 있으므로 업데이트
                        existingGame.setBroadcastChannel(gameData.getBroadcastChannel());

                        gameRepository.save(existingGame); // 변경된 엔티티 저장 (업데이트)
                    } else {
                        // 6. 오늘 경기이지만 DB에 없는 경우: 신규 경기이므로 저장 (혹시 모를 상황 대비)
                        // 예를 들어, 오늘 갑자기 추가된 경기가 있을 수 있습니다.
                        System.out.println("오늘 경기이지만 DB에 없음. 신규 저장: " + gameData.getGameCode());
                        GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);

                        // Stadium, Team 엔티티 조회 (NullPointerException 방지를 위해 null 체크 필요)
                        Stadium stadium = stadiumRepository.findById(saveDTO.getStadiumId());
                        Team homeTeam = teamRepository.findById(saveDTO.getHomeTeamId());
                        Team awayTeam = teamRepository.findById(saveDTO.getAwayTeamId());

                        if (stadium == null || homeTeam == null || awayTeam == null) {
                            System.out.println("신규 경기 저장 실패: 연관 데이터(경기장/팀) 없음 - " + saveDTO.getGameCode());
                            continue;
                        }

                        Game newGame = Game.builder()
                                .gameCode(saveDTO.getGameCode())
                                .stadium(stadium)
                                .homeTeam(homeTeam)
                                .awayTeam(awayTeam)
                                .gameTime(saveDTO.getGameTime())
                                .gameStatus(saveDTO.getGameStatus())
                                .homeResultScore(saveDTO.getHomeResultScore())
                                .awayResultScore(saveDTO.getAwayResultScore())
                                .broadcastChannel(saveDTO.getBroadcastChannel())
                                .homePredictionScore(saveDTO.getHomePredictionScore())
                                .awayPredictionScore(saveDTO.getAwayPredictionScore())
                                .totalPredictionScore(saveDTO.getTotalPredictionScore())
                                .homeWinPer(saveDTO.getHomeWinPer())
                                .awayWinPer(saveDTO.getAwayWinPer())
                                .build();

                        gameRepository.save(newGame); // 새로운 경기 저장
                    }
                } else {
                    // gameCode가 null인 오늘 경기는 처리 불가 (업데이트/신규 저장 대상 아님)
                    System.out.println("오늘 경기이나 gameCode가 null. 업데이트 불가: " + gameData.getGameTime());
                }
            }
        } catch (Exception e) {
            System.err.println("오늘 경기 업데이트 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("오늘 경기 업데이트 실패: " + e.getMessage(), e); // 트랜잭션 롤백을 위해 예외 다시 던지기
        } finally {
            if (driver != null) {
                try {
                    Thread.sleep(3000); // WebDriver 종료 전 잠시 대기
                    driver.quit();
                } catch (InterruptedException e) {
                    System.err.println("WebDriver 종료 중 인터럽트 발생: " + e.getMessage());
                    Thread.currentThread().interrupt(); // 인터럽트 상태 복원
                }
            }
        }
    }
}
