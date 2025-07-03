package com.example.ballkkaye.game.today;

import com.example.ballkkaye._core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayGameController {

    private final TodayGameService todayGameService;

    // 30분마다 자동 실행
    @Scheduled(cron = "0 0/30 * * * *", zone = "Asia/Seoul")
    public void scheduledUpdateTodayGames() {
        todayGameService.updateTodayGames();
    }

    @GetMapping("/api/admin/today-games/init")
    public ResponseEntity<?> initTodayGames() {
        todayGameService.updateTodayGames();
        return Resp.ok("오늘의 경기 갱신 완료");
    }
}