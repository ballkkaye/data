package com.example.ballkkaye.user.userPrediction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPredictionService {
    private final UserPredictionRepository userPredictionRepository;
}
