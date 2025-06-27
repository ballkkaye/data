package com.example.ballkkaye.player.startingPitcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StartingPitcherController {
    private final StartingPitcherService startingPitcherService;
}