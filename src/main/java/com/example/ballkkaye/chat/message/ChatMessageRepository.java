package com.example.ballkkaye.chat.message;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatMessageRepository {
    private final EntityManager em;
}
