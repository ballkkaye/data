package com.example.ballkkaye.player.startingPitcher;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StartingPitcherRepository {
    private final EntityManager em;
}
