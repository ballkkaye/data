package com.example.ballkkaye.game;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


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


    // 오늘 날짜의 경기 전체 조회 (gameId, gameTime, stadiumId 포함)
    public List<Game> findByToday() {
        try {
            LocalDate today = LocalDate.now(); // ex) 2025-07-04
            LocalDateTime startOfDay = today.atStartOfDay(); // 2025-07-04 00:00:00
            LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 2025-07-05 00:00:00

            return em.createQuery("""
                                SELECT g
                                FROM Game g
                                WHERE g.gameTime >= :start AND g.gameTime < :end
                            """, Game.class)
                    .setParameter("start", Timestamp.valueOf(startOfDay))
                    .setParameter("end", Timestamp.valueOf(endOfDay))
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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


    public Optional<Game> findById(Integer id) {
        try {
            Game game = em.find(Game.class, id);
            return Optional.ofNullable(game);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

