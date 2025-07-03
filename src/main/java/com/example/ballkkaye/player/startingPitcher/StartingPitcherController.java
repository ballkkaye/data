package com.example.ballkkaye.player.startingPitcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StartingPitcherController {
    private final StartingPitcherService startingPitcherService;

    // ① 월~토 17:00 ~ 23:50 (10분 간격)
//    @Scheduled(cron = "0 */10 17-23 * * MON-SAT", zone = "Asia/Seoul")
    public void syncEveningStartingPitchers() {
        startingPitcherService.startingPitchers();
    }

    // ② 화~일 00:00 ~ 12:00 (10분 간격) → 전날 밤 작업 이어받음
//    @Scheduled(cron = "0 */10 0-12 * * TUE-SUN", zone = "Asia/Seoul")
    public void syncMorningStartingPitchers() {
        startingPitcherService.startingPitchers();
    }


    // 관리자용 - role: admin / bot: 자동화
    @GetMapping("/admin/bot/starting-pitcher")
    public String StartingPitchers() {
        startingPitcherService.startingPitchers();
        return "크롤링 완료"; // TODO: 관리자 페이지 만들어지면 수정
    }
}