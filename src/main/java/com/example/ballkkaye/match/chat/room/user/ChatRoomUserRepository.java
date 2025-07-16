package com.example.ballkkaye.match.chat.room.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomUserRepository {
    private final EntityManager em;
}