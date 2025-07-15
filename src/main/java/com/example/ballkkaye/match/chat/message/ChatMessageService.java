package com.example.ballkkaye.match.chat.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
}
