package com.example.ballkkaye.player.hitterLineup.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TodayHitterLineupRepository {
    private final EntityManager em;
}
