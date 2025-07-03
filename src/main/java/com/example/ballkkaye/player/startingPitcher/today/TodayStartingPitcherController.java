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
    public void copyTodayStartingPitchersScheduled() {
        todayStartingPitcherService.copyTodayStartingPitchers();
    }

    @GetMapping("/admin/bot/copy-today-starting-pitchers")
    public String copyTodayStartingPitchers() {
        todayStartingPitcherService.copyTodayStartingPitchers();
        return "오늘 경기 선발투수 복사 완료";
    }

}
