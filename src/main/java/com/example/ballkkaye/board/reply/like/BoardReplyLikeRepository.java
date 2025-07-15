package com.example.ballkkaye.board.reply.like;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardReplyLikeRepository {
    private final EntityManager em;
}
