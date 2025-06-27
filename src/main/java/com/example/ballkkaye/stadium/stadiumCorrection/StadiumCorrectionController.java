package com.example.ballkkaye.stadium.stadiumCorrection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StadiumCorrectionController {
    private final StadiumCorrectionService stadiumCorrectionService;
}