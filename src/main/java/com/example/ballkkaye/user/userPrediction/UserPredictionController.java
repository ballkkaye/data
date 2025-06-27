package com.example.ballkkaye.user.userPrediction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserPredictionController {
    private final UserPredictionService userPredictionService;
}