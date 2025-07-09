package com.example.ballkkaye.player.startingPitcher;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StartingPitcherController {
    private final StartingPitcherService startingPitcherService;

    // ① 월~토 17:00 ~ 23:50 (10분 간격)
    @Scheduled(cron = "0 */10 17-23 * * MON-SAT", zone = "Asia/Seoul")
    public void scheduledSyncEveningStartingPitchers() {
        startingPitcherService.startingPitchers();
    }

    // ② 화~일 00:00 ~ 12:00 (10분 간격) → 전날 밤 작업 이어받음
    @Scheduled(cron = "0 */10 0-12 * * TUE-SUN", zone = "Asia/Seoul")
    public void scheduledSyncMorningStartingPitchers() {
        startingPitcherService.startingPitchers();
    }

    // 관리자용 선발 투수 insert
    @GetMapping("/s/admin/bot/starting-pitcher")
    public void adminStartingPitchers() {
        startingPitcherService.startingPitchers();
    }
}