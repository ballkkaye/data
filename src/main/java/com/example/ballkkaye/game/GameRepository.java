package com.example.ballkkaye.game;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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

    public boolean existsByGameTimeAndTeams(Timestamp gameTime, Integer homeTeamId, Integer awayTeamId) {
        Long count = em.createQuery("""
                            SELECT COUNT(g)
                            FROM Game g
                            WHERE g.gameTime = :gameTime
                            AND g.homeTeam.id = :homeTeamId
                            AND g.awayTeam.id = :awayTeamId
                        """, Long.class)
                .setParameter("gameTime", gameTime)
                .setParameter("homeTeamId", homeTeamId)
                .setParameter("awayTeamId", awayTeamId)
                .getSingleResult();

        return count > 0;
    }

    public List<Game> todayGame(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return em.createQuery("select g from Game g where g.gameTime >= :start and g.gameTime < :end", Game.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public boolean existsByGameCode(String gameCode) {
        Long count = em.createQuery("""
                            SELECT COUNT(g)
                            FROM Game g
                            WHERE g.gameCode = :gameCode
                        """, Long.class)
                .setParameter("gameCode", gameCode)
                .getSingleResult();
        return count > 0;
    }

    public Game findByGameCode(String gameCode) {
        List<Game> result = em.createQuery(
                        "SELECT t FROM TodayGame t WHERE t.gameCode = :gameCode", Game.class)
                .setParameter("gameCode", gameCode)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }


}
