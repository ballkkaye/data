package com.example.ballkkaye.player.hitterLineup;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HitterLineupController {
    private final HitterLineupService hitterLineUpService;
}