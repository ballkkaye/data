package com.example.ballkkaye.stadium;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StadiumController {
    private final StadiumService stadiumService;
}