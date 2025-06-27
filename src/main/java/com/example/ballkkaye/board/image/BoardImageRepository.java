package com.example.ballkkaye.board.image;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardImageRepository {
    private final EntityManager em;
}
