package com.example.ballkkaye.player.hitterLineup;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HitterLineupRepository {
    private final EntityManager em;
}
