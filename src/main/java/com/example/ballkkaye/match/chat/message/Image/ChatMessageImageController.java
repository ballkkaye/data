package com.example.ballkkaye.match.chat.message.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatMessageImageController {
    private final ChatMessageImageService chatMessageImageService;
}