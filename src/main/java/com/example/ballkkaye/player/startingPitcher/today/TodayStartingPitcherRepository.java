package com.example.ballkkaye.player.startingPitcher.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayStartingPitcherRepository {
    private final EntityManager em;

    public boolean existsAny() {
        Long count = em.createQuery("SELECT COUNT(t) FROM TodayStartingPitcher t", Long.class)
                .getSingleResult();
        return count > 0;
    }

    public void deleteAll() {
        em.createQuery("DELETE FROM TodayStartingPitcher").executeUpdate();
    }

    public void saveAll(List<TodayStartingPitcher> pitchers) {
        for (TodayStartingPitcher p : pitchers) {
            em.persist(p);
        }
    }
}
