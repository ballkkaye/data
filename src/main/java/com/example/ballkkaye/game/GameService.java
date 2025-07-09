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
import java.util.Optional;
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

                    GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);

                    if (saveDTO.getAwayTeamId() == null || saveDTO.getHomeTeamId() == null) {
                        continue;
                    }

                    Stadium stadium = stadiumRepository.findById(saveDTO.getStadiumId())
                            .orElseThrow(() -> new IllegalArgumentException("Stadium not found"));
                    Team homeTeam = teamRepository.findById(saveDTO.getHomeTeamId())
                            .orElseThrow(() -> new RuntimeException("homeTeam 찾을 수 없음: id=" + saveDTO.getHomeTeamId()));

                    Team awayTeam = teamRepository.findById(saveDTO.getAwayTeamId())
                            .orElseThrow(() -> new RuntimeException("awayTeam 찾을 수 없음: id=" + saveDTO.getAwayTeamId()));

                    Game game = Game.builder()
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
            throw new RuntimeException("크롤링 중 오류 발생", e);
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

            boolean matched = false;
            if (compact.matcher(rawGame).find()) {
                Matcher m = compact.matcher(rawGame);
                m.find();
                String awayTeamName = m.group(1);
                String homeTeamName = m.group(4);

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);
                awayScore = Integer.parseInt(m.group(2));
                homeScore = Integer.parseInt(m.group(3));
            } else if (spaced.matcher(rawGame).find()) {
                Matcher m = spaced.matcher(rawGame);
                m.find();
                String awayTeamName = m.group(1);
                String homeTeamName = m.group(2);

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);
                awayScore = Integer.parseInt(m.group(3));
                homeScore = Integer.parseInt(m.group(4));
            } else if (plain.matcher(rawGame).find()) {
                Matcher m = plain.matcher(rawGame);
                m.find();
                String awayTeamName = m.group(1);
                String homeTeamName = m.group(2);

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);
            }

            if (awayTeamId == null || homeTeamId == null) {
                throw new RuntimeException("팀 이름 매핑 실패: away or home team ID is null");
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

            result.add(new GameData(gameTime, gameStatus, homeScore, awayScore, channel,
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
        private Timestamp gameTime;
        private GameStatus gameStatus;
        private Integer homeResultScore;
        private Integer awayResultScore;
        private BroadcastChannel broadcastChannel;
        private Integer stadiumId;
        private Integer homeTeamId;
        private Integer awayTeamId;

        public GameData(Timestamp gameTime, GameStatus gameStatus,
                        Integer homeResultScore, Integer awayResultScore,
                        BroadcastChannel broadcastChannel, Integer stadiumId,
                        Integer homeTeamId, Integer awayTeamId) {
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

    @Transactional
    public void update() {
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

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#ddlMonth option")));

            Select ddlMonthSelect = new Select(ddlMonthElement);

            LocalDate today = LocalDate.now();
            String currentMonthValue = String.format("%02d", today.getMonthValue());
            ddlMonthSelect.selectByValue(currentMonthValue);

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tblScheduleList tbody td")));
            } catch (TimeoutException e) {
                return;
            }

            List<GameData> parsedGameDataList = extractGameData(driver, wait);

            for (GameData gameData : parsedGameDataList) {
                if (gameData.getGameTime() == null || !gameData.getGameTime().toLocalDateTime().toLocalDate().isEqual(today)) {
                    continue;
                }

                if (gameData.getStadiumId() == null || gameData.getHomeTeamId() == null ||
                        gameData.getAwayTeamId() == null || gameData.getGameTime() == null ||
                        gameData.getBroadcastChannel() == null) {
                    continue;
                }

                GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);

                Optional<Game> optionalGame = gameRepository.findByStadiumIdAndHomeTeamIdAndAwayTeamIdAndGameTime(
                        saveDTO.getStadiumId(),
                        saveDTO.getHomeTeamId(),
                        saveDTO.getAwayTeamId(),
                        saveDTO.getGameTime()
                );

                if (optionalGame.isPresent()) {
                    Game existingGame = optionalGame.get();
                    existingGame.update(
                            gameData.getGameStatus(),
                            gameData.getHomeResultScore(),
                            gameData.getAwayResultScore()
                    );
                } else {
                    Stadium stadium = stadiumRepository.findById(saveDTO.getStadiumId())
                            .orElseThrow(() -> new IllegalArgumentException("Stadium not found"));
                    Team homeTeam = teamRepository.findById(saveDTO.getHomeTeamId())
                            .orElseThrow(() -> new RuntimeException("homeTeam not found"));
                    Team awayTeam = teamRepository.findById(saveDTO.getAwayTeamId())
                            .orElseThrow(() -> new RuntimeException("awayTeam not found"));

                    if (stadium == null || homeTeam == null || awayTeam == null) {
                        continue;
                    }

                    Game newGame = Game.builder()
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

                    gameRepository.save(newGame);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("오늘 경기 업데이트 실패: " + e.getMessage(), e);
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
}
