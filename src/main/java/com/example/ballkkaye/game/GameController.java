package com.example.ballkkaye.game;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    // 정규 리그 전체 경기 일정
    @Scheduled(cron = "0 0 0 1 2 *", zone = "Asia/Seoul") // 매년 2월 1일 00시 정각
    public void scheduledSave() {
        gameService.save();
    }

    // 오늘 날짜 경기 갱신
    @Scheduled(cron = "0 50 23 * * *", zone = "Asia/Seoul") // 매일 오후 11시 50분
    public void scheduledUpdate() {
        gameService.update();
    }

    // 관리자용 전체 경기 일정 insert
    @GetMapping("/s/admin/bot/gameCrawlAndSave")
    public void adminSave() {
        gameService.save();
    }

    // 관리자용 오늘 날짜 경기 갱신
    @GetMapping("/s/admin/bot/gameCrawlAndUpdate")
    public void adminUpdate() {
        gameService.update();
    }
}