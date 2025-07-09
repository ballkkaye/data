package com.example.ballkkaye.user.userPrediction;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserPredictionController {
    private final UserPredictionService userPredictionService;

    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    public void scheduledEvaluatePredictions() {
        userPredictionService.evaluatePredictions();
    }

    // 관리자용 유저 예측 결과 판별
    @PostMapping("/s/admin/evaluate")
    public void adminEvaluatePredictions() {
        userPredictionService.evaluatePredictions();
    }
}