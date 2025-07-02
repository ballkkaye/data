package com.example.ballkkaye.game.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayGameController {

    private final TodayGameService todayGameService;

//    @GetMapping("/api/admin/today-games/init")
//    public ResponseEntity<?> initTodayGames() {
//        todayGameService.updateTodayGames();
//        return Resp.ok("오늘의 경기 갱신 완료");
//    }
//
//    @GetMapping("/api/today-games/prediction")
//    public ResponseEntity<?> getTodayPredictions() {
//        List<TodayGameResponse.PredictionDTO> respDTO = todayGameService.getTodayGamePredictions();
//        return Resp.ok(respDTO);
//    }
}