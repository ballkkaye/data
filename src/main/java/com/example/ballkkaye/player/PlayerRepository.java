package com.example.ballkkaye.player;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlayerRepository {
    private final EntityManager em;
}
