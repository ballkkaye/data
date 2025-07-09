package com.example.ballkkaye.player.startingPitcher.today;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayStartingPitcherController {
    private final TodayStartingPitcherService todayStartingPitcherService;

    @Scheduled(cron = "0 */10 0-5 * * *", zone = "Asia/Seoul")
    public void scheduledCopyTodayStartingPitchers() {
        todayStartingPitcherService.copyTodayStartingPitchers();
    }

    // 관리자용 오늘의 선발 투수
    @GetMapping("/s/admin/bot/copy-today-starting-pitchers")
    public void adminCopyTodayStartingPitchers() {
        todayStartingPitcherService.copyTodayStartingPitchers();
    }

}
