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
            // 크롬 드라이버 경로 설정
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--headless=new"); // 필요시 주석 해제하여 헤드리스 모드 사용

            driver = new ChromeDriver(options);
            String url = "https://www.koreabaseball.com/Schedule/Schedule.aspx#";

            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ddlYear")));
            WebElement ddlMonthElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ddlMonth")));
            System.out.println("페이지 기본 요소 (ddlYear, ddlMonth) 로드 완료.");

            Select ddlMonthSelect = new Select(ddlMonthElement);

            List<WebElement> monthOptions = ddlMonthSelect.getOptions();

            for (WebElement monthOption : monthOptions) {
                String monthText = monthOption.getText().trim();
                String monthValue = monthOption.getAttribute("value").trim();

                // 'value'가 두 자리 숫자인지 확인하고, 3부터 8 사이의 월만 선택
                if (monthValue.matches("\\d{2}")) {
                    int monthNum = Integer.parseInt(monthValue);
                    if (monthNum >= 3 && monthNum <= 8) { // 3월부터 8월까지
                        System.out.println("\n--- " + monthText + " 경기 정보 추출 시작 ---");

                        // 현재 선택된 월이 아닌 경우에만 드롭다운을 변경하고 대기
                        // 페이지 첫 로딩 시 기본 월(대부분 현재 월)은 이미 로드되어 있으므로 불필요한 대기 방지
                        if (!ddlMonthSelect.getFirstSelectedOption().getAttribute("value").equals(monthValue)) {
                            ddlMonthSelect.selectByValue(monthValue);

                            // 월 변경 후 페이지 업데이트 대기
                            // #tblScheduleList tbody 내부에 어떤 <td> 요소라도 나타날 때까지 기다립니다.
                            // 이는 경기가 있든 없든 (예: "등록된 일정이 없습니다.") 테이블 내용이 로드되었음을 의미합니다.
                            try {
                                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tblScheduleList tbody td")));
                            } catch (TimeoutException e) {
                                System.out.println("정보: " + monthText + "에 경기 데이터가 없거나 페이지 로드에 실패했습니다. 다음 월로 이동합니다. (TimeoutException)");
                                continue; // 해당 월 처리 스킵하고 다음 월로 이동
                            }
                        }

                        // extractGameData 메서드 호출. 이 메서드가 GameData 리스트를 반환합니다.
                        List<GameData> parsedGameDataList = extractGameData(driver, wait);

                        // 파싱된 GameData를 Game 엔티티로 변환하여 DB에 저장
                        for (GameData gameData : parsedGameDataList) {
                            // 필수 필드 누락 시 스킵: gameTime, stadiumId, homeTeamId, awayTeamId, broadcastChannel
                            if (gameData.getStadiumId() == null || gameData.getHomeTeamId() == null ||
                                    gameData.getAwayTeamId() == null || gameData.getGameTime() == null ||
                                    gameData.getBroadcastChannel() == null) {
                                System.err.println("경고: 필수 필드 누락으로 경기 저장 스킵: " + gameData);
                                continue;
                            }

                            GameRequest.SaveDTO saveDTO = GameRequest.SaveDTO.fromGameData(gameData);

                            Stadium stadiumPS = stadiumRepository.findById(saveDTO.getStadiumId());

                            Team homeTeamPS = teamRepository.findById(saveDTO.getHomeTeamId());

                            Team awayTeamPS = teamRepository.findById(saveDTO.getAwayTeamId());
                            Game game = Game.builder()
                                    .stadium(stadiumPS)
                                    .homeTeam(homeTeamPS)
                                    .awayTeam(awayTeamPS)
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
                            System.out.println("DB 저장 완료: " + game);
                        }
                        System.out.println("--- " + monthText + " 경기 정보 추출 및 DB 저장 완료 ---\n");
                    } else {
                        System.out.println("DEBUG: Skipping month " + monthText + " (월 범위 3~8에 해당하지 않음).");
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace(); // 일반적인 크롤링 오류를 catch
            System.err.println("크롤링 및 저장 중 치명적인 오류 발생: " + e.getMessage());
        } finally {
            if (driver != null) {
                try {
                    Thread.sleep(3000);
                    driver.quit();
                    System.out.println("웹 드라이버 종료");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("웹 드라이버 종료 중 인터럽트 발생: " + e.getMessage());
                }
            }
        }
    }

    private List<GameData> extractGameData(WebDriver driver, WebDriverWait wait) {
        List<GameData> parsedGameDataList = new ArrayList<>();

        // scheduleTable과 tbody를 extractGameData 메서드 내부에서 다시 찾습니다.
        // 이는 월 변경 시 이전 요소가 StaleElementReferenceException을 일으킬 수 있기 때문입니다.
        WebElement scheduleTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tblScheduleList")));
        WebElement tbody = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(scheduleTable, By.tagName("tbody")));

        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        String currentDate = "";

        int currentYear = LocalDate.now().getYear(); // 현재 년도 사용

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            if (cells.isEmpty()) {
                continue;
            }

            // 첫 번째 셀이 "등록된 일정이 없습니다." 같은 메시지인 경우 스킵
            // KBO 웹사이트의 빈 달은 보통 <td colspan="9">등록된 일정이 없습니다.</td> 이런 식입니다.
            if (cells.size() == 1 && cells.get(0).getText().contains("등록된 일정이 없습니다.")) {
                System.out.println("정보: '등록된 일정이 없습니다.' 메시지 행을 스킵합니다.");
                continue;
            }


            String rawDate = "";
            String rawTime = "";
            String rawGame = "";
            String rawTvChannel = "";
            String rawStadium = "";
            String rawRemarks = "";

            String firstCellText = cells.get(0).getText().trim();

            if (firstCellText.matches("^\\d{2}\\.\\d{2}\\((월|화|수|목|금|토|일)\\)$")) {
                currentDate = firstCellText;
                rawDate = currentDate;

                if (cells.size() > 1) rawTime = cells.get(1).getText().trim();
                if (cells.size() > 2) rawGame = cells.get(2).getText().trim();
                if (cells.size() > 5) {
                    WebElement tvCell = cells.get(5);
                    List<WebElement> imgTags = tvCell.findElements(By.tagName("img"));
                    if (!imgTags.isEmpty()) {
                        rawTvChannel = imgTags.get(0).getAttribute("alt").trim();
                    } else {
                        rawTvChannel = tvCell.getText().trim();
                    }
                }
                if (cells.size() > 7) rawStadium = cells.get(7).getText().trim();
                if (cells.size() > 8) rawRemarks = cells.get(8).getText().trim();

            } else { // 날짜 없이 시간부터 시작하는 연속된 행
                rawDate = currentDate; // 이전 행의 날짜 재사용

                rawTime = firstCellText;
                if (cells.size() > 1) rawGame = cells.get(1).getText().trim();
                if (cells.size() > 4) {
                    WebElement tvCell = cells.get(4);
                    List<WebElement> imgTags = tvCell.findElements(By.tagName("img"));
                    if (!imgTags.isEmpty()) {
                        rawTvChannel = imgTags.get(0).getAttribute("alt").trim();
                    } else {
                        rawTvChannel = tvCell.getText().trim();
                    }
                }
                if (cells.size() > 6) rawStadium = cells.get(6).getText().trim();
                if (cells.size() > 7) rawRemarks = cells.get(7).getText().trim();
            }

            Timestamp gameTime = null;
            Integer homeResultScore = null;
            Integer awayResultScore = null;
            GameStatus gameStatus = GameStatus.SCHEDULED;
            BroadcastChannel broadcastChannel = BroadcastChannel.UNKNOWN;
            Integer stadiumId = null;
            Integer homeTeamId = null;
            Integer awayTeamId = null;

            String datePart = rawDate.replaceAll("\\(.*\\)", "").trim();
            String dateTimeString = String.format("%d.%s %s", currentYear, datePart, rawTime);
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
                gameTime = Timestamp.valueOf(localDateTime);
            } catch (DateTimeParseException e) {
                System.err.println("날짜/시간 파싱 오류: " + dateTimeString + " - " + e.getMessage());
                gameTime = null;
            }

            if (rawGame.contains("리뷰") || rawGame.contains("프리뷰")) {
                continue;
            }

            Pattern compactScorePattern = Pattern.compile("([가-힣A-Z]+)(\\d+)vs(\\d+)([가-힣A-Z]+)");
            Matcher compactScoreMatcher = compactScorePattern.matcher(rawGame);

            Pattern spacedScorePattern = Pattern.compile("([가-힣A-Z]+)vs([가-힣A-Z]+)\\s*(\\d+)(?::|vs)(\\d+)");
            Matcher spacedScoreMatcher = spacedScorePattern.matcher(rawGame);

            Pattern scheduledGamePattern = Pattern.compile("([가-힣A-Z]+)vs([가-힣A-Z]+)");
            Matcher scheduledGameMatcher = scheduledGamePattern.matcher(rawGame);

            boolean matched = false;

            if (compactScoreMatcher.find()) {
                String awayTeamName = compactScoreMatcher.group(1);
                awayResultScore = Integer.parseInt(compactScoreMatcher.group(2));
                homeResultScore = Integer.parseInt(compactScoreMatcher.group(3));
                String homeTeamName = compactScoreMatcher.group(4);

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);

                gameStatus = GameStatus.COMPLETED;
                matched = true;
            } else if (spacedScoreMatcher.find()) {
                String awayTeamName = spacedScoreMatcher.group(1);
                String homeTeamName = spacedScoreMatcher.group(2);
                awayResultScore = Integer.parseInt(spacedScoreMatcher.group(3));
                homeResultScore = Integer.parseInt(spacedScoreMatcher.group(4));

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);

                gameStatus = GameStatus.COMPLETED;
                matched = true;
            } else if (scheduledGameMatcher.find()) {
                String awayTeamName = scheduledGameMatcher.group(1);
                String homeTeamName = scheduledGameMatcher.group(2);

                awayTeamId = UtilMapper.getTeamId(awayTeamName);
                homeTeamId = UtilMapper.getTeamId(homeTeamName);

                matched = true;
            }

            if (rawRemarks.contains("우천취소")) {
                gameStatus = GameStatus.CANCELLED;
            } else if (gameTime != null && gameTime.toLocalDateTime().isBefore(LocalDateTime.now()) && !matched) {
                gameStatus = GameStatus.COMPLETED;
            } else if (!matched) {
                gameStatus = GameStatus.SCHEDULED;
            } else if (matched && gameStatus != GameStatus.COMPLETED) {
                if (gameTime != null && gameTime.toLocalDateTime().isBefore(LocalDateTime.now())) {
                    gameStatus = GameStatus.COMPLETED;
                } else {
                    gameStatus = GameStatus.SCHEDULED;
                }
            }


            if (!rawTvChannel.isEmpty()) {
                String[] channelCodeArray = rawTvChannel.split("[\\n\\s,]+");
                for (String code : channelCodeArray) {
                    BroadcastChannel channel = BroadcastChannel.fromString(code);
                    if (channel != BroadcastChannel.UNKNOWN) {
                        broadcastChannel = channel;
                        break;
                    }
                }
            }

            if (!rawStadium.isEmpty()) {
                stadiumId = UtilMapper.getStadiumId(rawStadium);
                if (stadiumId == null) {
                    System.err.println("경고: 알 수 없는 구장 이름: " + rawStadium + " (ID 매핑 실패)");
                }
            }

            parsedGameDataList.add(new GameData(
                    gameTime,
                    gameStatus,
                    homeResultScore,
                    awayResultScore,
                    broadcastChannel,
                    stadiumId,
                    homeTeamId,
                    awayTeamId
            ));
        }
        return parsedGameDataList;
    }

    // 크롤링 파서용 내부 모델
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

        public GameData(Timestamp gameTime, GameStatus gameStatus, Integer homeResultScore, Integer awayResultScore,
                        BroadcastChannel broadcastChannel, Integer stadiumId, Integer homeTeamId, Integer awayTeamId) {
            this.gameTime = gameTime;
            this.gameStatus = gameStatus;
            this.homeResultScore = homeResultScore;
            this.awayResultScore = awayResultScore;
            this.broadcastChannel = broadcastChannel;
            this.stadiumId = stadiumId;
            this.homeTeamId = homeTeamId;
            this.awayTeamId = awayTeamId;
        }

        @Override
        public String toString() {
            return "GameData{" +
                    "gameTime=" + gameTime +
                    ", gameStatus=" + gameStatus +
                    ", homeResultScore=" + homeResultScore +
                    ", awayResultScore=" + awayResultScore +
                    ", broadcastChannel=" + broadcastChannel +
                    ", stadiumId=" + stadiumId +
                    ", homeTeamId=" + homeTeamId +
                    ", awayTeamId=" + awayTeamId +
                    '}';
        }
    }

}
