package com.example.ballkkaye.match.chat.message.Image;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatMessageImageRepository {
    private final EntityManager em;
}
