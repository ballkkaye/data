package com.example.ballkkaye.game.today;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayGameController {

    private final TodayGameService todayGameService;

    @GetMapping("/api/today-games")
    public ResponseEntity<?> getTodayGames() {
        TodayGameResponse.TodayListResponse respDTO = todayGameService.getTodayGames();
        return Resp.ok(respDTO);
    }
}