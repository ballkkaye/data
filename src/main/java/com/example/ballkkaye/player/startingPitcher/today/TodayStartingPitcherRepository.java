package com.example.ballkkaye.player.startingPitcher.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TodayStartingPitcherRepository {
    private final EntityManager em;
}
