package com.example.ballkkaye.user.userPrediction;

import com.example.ballkkaye._core.util.Resp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserPredictionController {
    private final UserPredictionService userPredictionService;

    @PostMapping("/admin/evaluate")
    public ResponseEntity<?> evaluatePredictions() {
        userPredictionService.evaluatePredictions();
        return Resp.ok("ok");
    }
}