package com.example.ballkkaye.team.record;

import com.example.ballkkaye._core.util.UtilMapper;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.TeamRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TeamRecordService {
    private final TeamRecordRepository teamRecordRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void saveBot() {

        // 크롬 드라이버 경로 설정
        WebDriverManager.chromedriver().setup();

        // 크롬 브라우저 옵션 설정 (최대화, 팝업 차단 해제) << merge 할 때는 --headless=new 로 실행하고 눈으로 확인할 때는 --start-maximized 로 실행하면 됨
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-popup-blocking");


        // WebDriver 객체 생성
        WebDriver driver = new ChromeDriver(options);
        List<Integer> runList = new ArrayList<>();
        List<Double> opsList = new ArrayList<>();
        List<Double> eraList = new ArrayList<>();

        try {
            // 팀별 run 득점 가져오기
            String url = "https://www.koreabaseball.com/Record/Team/Hitter/Basic1.aspx";
            driver.get(url);
            Thread.sleep(2000);
            List<WebElement> runCells = driver.findElements(By.cssSelector("td[data-id='RUN_CN']"));
            for (WebElement runCell : runCells) {
                runList.add(Integer.parseInt(runCell.getText().trim()));
            }

            // 팀별 ops 가져오기
            url = "https://www.koreabaseball.com/Record/Team/Hitter/Basic2.aspx";
            driver.get(url);
            Thread.sleep(2000);
            List<WebElement> opsCells = driver.findElements(By.cssSelector("td[data-id='OPS_RT']"));
            for (WebElement opsCell : opsCells) {
                opsList.add(Double.parseDouble(opsCell.getText().trim()));
            }

            // 팀별 era 가져오기
            url = "https://www.koreabaseball.com/Record/Team/Pitcher/Basic1.aspx";
            driver.get(url);
            Thread.sleep(2000);
            List<WebElement> eraCells = driver.findElements(By.cssSelector("td[data-id='ERA_RT']"));
            for (WebElement eraCell : eraCells) {
                eraList.add(Double.parseDouble(eraCell.getText().trim()));
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<TeamRecordRequest.Dto> dtoList = new ArrayList<>();
        try {
            String url = "https://www.koreabaseball.com/Record/TeamRank/TeamRankDaily.aspx";
            driver.get(url);

            // JS가 테이블을 렌더링할 때까지 기다림 (테이블이 DOM에 그려질 때까지)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.tData tbody tr")));

            List<WebElement> rows = driver.findElements(By.cssSelector("table.tData tbody tr"));
            for (int i = 0; i < rows.size(); i++) {
                List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
                if (cols.size() < 10) {
                    log.warn("열 부족. 건너뜀");
                    continue;
                }

                Integer teamRank = Integer.parseInt(cols.get(0).getText().trim());
                String teamNameFull = cols.get(1).getText().trim();
                String teamName = teamNameFull.split(" ")[0];
                Integer teamId = UtilMapper.getTeamId(teamName);

                if (teamId == null) {
                    log.warn("teamId 매핑 실패: {}", teamNameFull);
                    continue;
                }
                Integer totalGame = Integer.parseInt(cols.get(2).getText().trim());
                Integer winGame = Integer.parseInt(cols.get(3).getText().trim());
                Integer loseGame = Integer.parseInt(cols.get(4).getText().trim());
                Integer tieGame = Integer.parseInt(cols.get(5).getText().trim());
                Double winRate = Double.parseDouble(cols.get(6).getText().trim());
                Double gap = cols.get(7).getText().trim().equals("-") ? 0.0 : Double.parseDouble(cols.get(7).getText().trim());
                String recentTen = cols.get(8).getText().trim();
                String streak = cols.get(9).getText().trim();

                Double OPS = opsList.get(i);
                Integer R = runList.get(i);
                Double ERA = eraList.get(i);

                TeamRecordRequest.Dto dto = new TeamRecordRequest.Dto(
                        teamId, teamRank, totalGame, winGame, loseGame, tieGame,
                        winRate, gap, recentTen, streak, OPS, R, ERA
                );
                dtoList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TeamRecord> entities = new ArrayList<>();

        for (TeamRecordRequest.Dto dto : dtoList) {
            Team team = teamRepository.findById(dto.getTeamId());

            TeamRecord entity = TeamRecord.builder()
                    .team(team)
                    .teamRank(dto.getTeamRank())
                    .totalGame(dto.getTotalGame())
                    .winGame(dto.getWinGame())
                    .loseGame(dto.getLoseGame())
                    .tieGame(dto.getTieGame())
                    .winRate(dto.getWinRate())
                    .gap(dto.getGap())
                    .recentTenGame(dto.getRecentTenGames())
                    .streak(dto.getStreak())
                    .OPS(dto.getOPS())
                    .R(dto.getR())
                    .ERA(dto.getERA())
                    .build();
            entities.add(entity);
        }
        if (entities.size() < 10) {
            throw new RuntimeException(); // TODO 이거 나중에 수제 예외처리로 바꿔야함
        }
        teamRecordRepository.saveAll(entities);
    }

}
