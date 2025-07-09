package com.example.ballkkaye.game;

import com.example.ballkkaye._core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    // 정규 리그 전체 경기 일정
    @Scheduled(cron = "0 0 0 1 2 *", zone = "Asia/Seoul") // 매년 2월 1일 00시 정각
    public void saveRegularSeason() {
        gameService.save();
    }

    // 포스트 리그 전체 경기 일정
    @Scheduled(cron = "0 0 0 ? 8-11 1", zone = "Asia/Seoul") // 매주 월요일 00시
    public void savePostSeason() {
        gameService.save();
    }

    // 관리자용
    @GetMapping("/admin/bot/gameCrawlAndSave")
    public ResponseEntity<?> adminSave() {
        gameService.save();
        return Resp.ok("ok");
    }

    // 오늘의 경기 갱신용 (game_tb가 업데이트 되어야 함)
    @GetMapping("/admin/bot/gameCrawlAndUpdate")
    public ResponseEntity<?> adminUpdate() {
        gameService.update();
        return ResponseEntity.ok("ok");
    }
}