package com.example.ballkkaye.player.hitterLineup.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayHitterLineupController {
    private final TodayHitterLineupService todayHitterLineUpService;
}