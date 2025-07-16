package com.example.ballkkaye.match.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MatchLikeController {
    private final MatchLikeService matchLikeService;
}
