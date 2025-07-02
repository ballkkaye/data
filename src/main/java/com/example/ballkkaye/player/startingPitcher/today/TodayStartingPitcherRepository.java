package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TodayStartingPitcherRepository {
    private final EntityManager em;

    public Double getPitcherEraByGameAndTeam(Game game, Team team) {
        return em.createQuery(
                        "SELECT t.ERA FROM TodayStartingPitcher t " +
                                "WHERE t.game = :game AND t.player.team = :team", Double.class)
                .setParameter("game", game)
                .setParameter("team", team)
                .getSingleResult();
    }
}
