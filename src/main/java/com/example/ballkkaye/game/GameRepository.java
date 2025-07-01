package com.example.ballkkaye.game;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Repository
public class GameRepository {

    private final EntityManager em;

    public Game save(Game game) {
        if (game.getId() == null) {
            em.persist(game);
        } else {
            game = em.merge(game);
        }
        return game;
    }

    public Game findGameByDateAndTeams(String gameDate, Integer homeTeamId, Integer awayTeamId) {
        try {
            LocalDate date = LocalDate.parse(gameDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            Timestamp startOfDay = Timestamp.valueOf(date.atStartOfDay());
            Timestamp endOfDay = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

            return em.createQuery("""
                                SELECT g
                                FROM Game g
                                WHERE g.gameTime >= :startOfDay
                                  AND g.gameTime < :endOfDay
                                  AND g.homeTeam.id = :homeTeamId
                                  AND g.awayTeam.id = :awayTeamId
                            """, Game.class)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay)
                    .setParameter("homeTeamId", homeTeamId)
                    .setParameter("awayTeamId", awayTeamId)
                    .getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}