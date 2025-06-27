package com.example.ballkkaye.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GameController {
    private final GameService gameService;
}