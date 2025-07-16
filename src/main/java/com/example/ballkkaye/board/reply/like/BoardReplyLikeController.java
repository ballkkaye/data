package com.example.ballkkaye.board.reply.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardReplyLikeController {
    private final BoardReplyLikeService boardReplyLikeService;
}
