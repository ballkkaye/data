package com.example.ballkkaye.player.hitterLineup.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayHitterLineupController {
    private final TodayHitterLineupService todayHitterLineUpService;


    //@Scheduled(cron = "0 */10 17-18 * * MON-FRI") // 평일 17:00 ~ 18:59 매 10분
    public void copyTodayLineupFromHitterLineupWeekday() {
        todayHitterLineUpService.copyTodayLineupFromHitterLineup();
    }

    //@Scheduled(cron = "0 */10 17-18 * * MON-FRI") // 평일 17:00 ~ 18:59 매 10분
    public void copyTodayLineupFromHitterLineupWeekend() {
        todayHitterLineUpService.copyTodayLineupFromHitterLineup();
    }


    @GetMapping("/admin/bot/today-hitter-lineup")
    public String copyTodayLineupFromHitterLineup() {
        todayHitterLineUpService.copyTodayLineupFromHitterLineup();
        return "오늘 경기 타자 라인업 복사 완료";
    }

}