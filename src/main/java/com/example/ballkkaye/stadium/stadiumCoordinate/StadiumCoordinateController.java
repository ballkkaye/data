package com.example.ballkkaye.stadium.stadiumCoordinate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StadiumCoordinateController {
    private final StadiumCoordinateService stadiumCoordinateService;
}