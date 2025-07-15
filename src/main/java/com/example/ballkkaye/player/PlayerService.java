package com.example.ballkkaye.player;

import com.example.ballkkaye._core.util.UtilMapper;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.TeamRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.sentry.Sentry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    // 선수 목록 저장 로직
    @Transactional
    public void saveBot() {
        // 크롬 드라이버 경로 설정
        WebDriverManager.chromedriver().setup();
        // 크롬 브라우저 옵션 설정 (최대화, 팝업 차단 해제) << merge 할 때는 --headless=new 로 실행하고 눈으로 확인할 때는 --start-maximized 로 실행하면 됨
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
        options.addArguments("--headless=new");
        options.addArguments("--disable-popup-blocking");

        // WebDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver(options);

        // 최종 서비스에 넘길 DTO
        List<PlayerRequest.Dto> matchedPlayers = new ArrayList<>();

        try {
            // KBO 선수 검색 페이지 접속
            String url = "https://www.koreabaseball.com/Player/Search.aspx";
            driver.get(url);
            Thread.sleep(2000); // 페이지 로딩 대기 << 혹시 로딩 안될 수도 있어서 기다려야 함.

            // 팀 선택 드롭다운 박스 요소 가져오기
            WebElement dropdownInit = driver.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam"));
            Select selectInit = new Select(dropdownInit);
            List<WebElement> optionsInit = selectInit.getOptions();

            // 드롭다운에서 각 팀의 value와 표시 이름 추출
            List<String> teamValues = new ArrayList<>();
            List<String> teamNames = new ArrayList<>();
            for (int i = 1; i < optionsInit.size(); i++) { // 0번은 팀 선택 이여서 제외하고
                teamValues.add(optionsInit.get(i).getAttribute("value")); // 예: HH  페이지에 태그 벨류가 HH, HT 같은 약자로 되어 있음. 이걸로 OPTION 선택해서 팀 단위 FOR 문 돌릴거임
                teamNames.add(optionsInit.get(i).getText());              // 예: LG 트윈스 선수 중에 팀이 고양 같은 상무 팀 소속이 잇어서 해당 선수들을 제외하려면 팀명과 같아야 함 그래서 옵션박스의 벨류가 아니라 보여지는 이름을 꺼내와서 비교하고 같으면 담으려고 한다.
            }

            // 각 팀별로 반복
            for (int i = 0; i < teamValues.size(); i++) {
                // 드롭다운을 다시 찾아 선택 (페이지 리렌더링되므로 다시 불러와야 함) << 이거 안넣으니까 자바 스크립이 넘어가면서 해당 요소를 찾지를 못해서 오류남
                WebElement dropdown = driver.findElement(By.id("cphContents_cphContents_cphContents_ddlTeam"));
                Select teamSelect = new Select(dropdown);

                String teamValue = teamValues.get(i);       // value 속성 (예: LG)
                String teamVisibleName = teamNames.get(i);  // 표시된 이름 (예: LG 트윈스)

                // 해당 팀 선택
                teamSelect.selectByValue(teamValue);
                Thread.sleep(2000); // 선택 후 데이터 로딩 대기

                int currentPage = 1;
                while (true) {
                    // 현재 페이지의 선수 리스트 추출
                    List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
                    for (WebElement row : rows) {
                        List<WebElement> cells = row.findElements(By.tagName("td"));
                        if (cells.size() < 3) continue; // td 로 되어있는게 3개보다 적으면 넘김 << 이상한 애들 없앰

                        // 선수 상세 페이지 링크와 이름, 팀명 추출
                        WebElement link = cells.get(1).findElement(By.tagName("a"));
                        String href = link.getAttribute("href");
                        String playerIdStr = href.substring(href.indexOf("playerId=") + 9);
                        Integer playerId = Integer.parseInt(playerIdStr);
                        String playerName = link.getText();
                        String playerTeam = cells.get(2).getText().trim(); // 예: LG 트윈스

                        // teamId를 먼저 가져옴 (이후 중복검사에도 사용됨)
                        Integer teamId = UtilMapper.getTeamId(playerTeam);
                        if (teamId == null) {
                            log.warn("매핑되지 않은 팀명: " + playerTeam);
                            continue;
                        }

                        // 중복 검사: 같은 이름 & 같은 팀 ID인 경우만 중복으로 간주
                        boolean isDuplicate = false;
                        for (PlayerRequest.Dto playerDto : matchedPlayers) {
                            if (playerDto.getName().equals(playerName) && playerDto.getTeamId().equals(teamId)) {
                                isDuplicate = true;
                                break;
                            }
                        }

                        // 팀명이 현재 선택된 팀명이면서 중복이 아닐 때만 저장
                        if (!isDuplicate && playerTeam.equals(teamVisibleName.trim())) {
                            matchedPlayers.add(new PlayerRequest.Dto(playerId, playerName, teamId));
                        } else if (isDuplicate) {
                            log.info("중복된 선수 발견, 추가하지 않음: " + playerName + " (" + playerTeam + ")");
                        }

                    }

                    // 다음 페이지 버튼 찾고 있으면 클릭
                    currentPage++;
                    String nextBtnId = "cphContents_cphContents_cphContents_ucPager_btnNo" + currentPage;
                    List<WebElement> nextPageBtnList = driver.findElements(By.id(nextBtnId));
                    if (!nextPageBtnList.isEmpty()) {
                        nextPageBtnList.get(0).click();
                        Thread.sleep(2000); // 페이지 로딩 대기
                    } else {
                        break; // 다음 페이지 없으면 -> 종료
                    }
                }
            }

        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("크롤링 중 오류 발생", e);
        } finally {
            // 크롬 드라이버 종료
            driver.quit();
        }

        List<Player> players = new ArrayList<>();

        for (PlayerRequest.Dto dto : matchedPlayers) {
            Team team = teamRepository.findById(dto.getTeamId())
                    .orElseThrow(() -> new RuntimeException("team not found: id=" + dto.getTeamId()));

            Player player = Player.builder()
                    .kboPlayerId(dto.getKboPlayerId())
                    .name(dto.getName())
                    .team(team)
                    .build();

            players.add(player);
        }

        playerRepository.saveAll(players);
    }
}
