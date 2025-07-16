package com.example.ballkkaye.board.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardLikeController {
    private final BoardLikeService boardLikeService;
}
