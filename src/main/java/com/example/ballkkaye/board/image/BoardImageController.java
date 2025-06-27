package com.example.ballkkaye.board.image;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardImageController {
    private final BoardImageService boardImageService;
}