package com.example.ballkkaye.board.reply;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardReplyRepository {
    private final EntityManager em;
}
