package com.example.ballkkaye.board.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardReplyController {
    private final BoardReplyService boardReplyService;
}