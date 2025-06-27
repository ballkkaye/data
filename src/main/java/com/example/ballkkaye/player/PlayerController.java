package com.example.ballkkaye.player;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlayerController {
    private final PlayerService playerService;

    // 선수 목록 저장 로직
    // 정기 실행: 매주 일요일 자정
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void saveBot() {
        // DB에 저장
        playerService.saveBot();
    }

    // 관리자용 API
    @GetMapping("/s/admin/bot/player")
    public void adminSaveBot() {
        playerService.saveBot();
    }
}