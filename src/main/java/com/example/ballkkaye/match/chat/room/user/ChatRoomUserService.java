package com.example.ballkkaye.match.chat.room.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomUserService {
    private final ChatRoomUserRepository chatRoomUserRepository;
}