package com.example.ballkkaye.game.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayGameRepository {

    private final EntityManager em;

    public void saveOrUpdate(TodayGame todayGame) {
        String gameCode = todayGame.getGameCode();

        TodayGame existing = findByGameCode(gameCode);

        if (existing == null) {
            em.persist(todayGame);
        } else {
            existing.update(
                    todayGame.getGameStatus(),
                    todayGame.getHomeResultScore(),
                    todayGame.getAwayResultScore()
            );
        }
    }


    public TodayGame findByGameCode(String gameCode) {
        List<TodayGame> result = em.createQuery(
                        "SELECT t FROM TodayGame t WHERE t.gameCode = :gameCode", TodayGame.class)
                .setParameter("gameCode", gameCode)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}