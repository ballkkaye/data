package com.example.ballkkaye.player;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlayerController {
    private final PlayerService playerService;
}