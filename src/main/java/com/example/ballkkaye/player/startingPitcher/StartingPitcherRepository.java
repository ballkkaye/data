package com.example.ballkkaye.player.startingPitcher;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class StartingPitcherRepository {
    private final EntityManager em;

    // 선발 투수 저장
    public void saveAll(List<StartingPitcher> entities) {
        for (StartingPitcher entity : entities) {
            em.persist(entity);
        }
    }
}
