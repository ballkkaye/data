package com.example.ballkkaye.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService {
    private final GameRepository gameRepository;
}
