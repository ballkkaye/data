package com.example.ballkkaye.player.startingPitcher.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayStartingPitcherController {
    private final TodayStartingPitcherService todayStartingPitcherService;
}
