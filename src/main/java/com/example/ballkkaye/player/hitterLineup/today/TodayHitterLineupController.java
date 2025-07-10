package com.example.ballkkaye.player.hitterLineup.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayHitterLineupController {
    private final TodayHitterLineupService todayHitterLineUpService;


    //@Scheduled(cron = "0 */5 13-18 * * *") // 매일 13:00~18:59까지 5분 간격
    public void scheduledCopyTodayLineupFromHitterLineupWeekend() {
        todayHitterLineUpService.copyTodayLineupFromHitterLineup();
    }


    @GetMapping("/s/admin/bot/today-hitter-lineup")
    public void adminCopyTodayLineupFromHitterLineup() {
        todayHitterLineUpService.copyTodayLineupFromHitterLineup();
    }

}
