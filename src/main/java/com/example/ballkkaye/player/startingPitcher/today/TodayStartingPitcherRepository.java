package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
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

    public Double getPitcherEraByGameAndTeam(Game game, Team team) {
        return em.createQuery(
                        "SELECT t.ERA FROM TodayStartingPitcher t " +
                                "WHERE t.game = :game AND t.player.team = :team", Double.class)
                .setParameter("game", game)
                .setParameter("team", team)
                .getSingleResult();
    }
}
