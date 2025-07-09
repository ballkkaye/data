package com.example.ballkkaye.game.today;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayGameController {

    private final TodayGameService todayGameService;

    // 30분마다 자동 실행
    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul")
    public void scheduledSyncTodayGames() {
        todayGameService.syncTodayGames();
    }

    // 관리자용 오늘 날짜 경기 갱신
    @GetMapping("/s/admin/today-games/sync")
    public void adminSyncTodayGames() {
        todayGameService.syncTodayGames();
    }
}