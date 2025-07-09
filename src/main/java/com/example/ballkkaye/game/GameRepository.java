package com.example.ballkkaye.game;

import com.example.ballkkaye.common.enums.GameStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Game> findTodayGame(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return em.createQuery("select g from Game g where g.gameTime >= :start and g.gameTime < :end", Game.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Game> findByGameStatusIn(List<GameStatus> gameStatusList) {
        String jpql = "SELECT g FROM Game g WHERE g.gameStatus IN :statuses";
        return em.createQuery(jpql, Game.class)
                .setParameter("statuses", gameStatusList)
                .getResultList();
    }

    public Optional<Game> findByStadiumIdAndHomeTeamIdAndAwayTeamIdAndGameTime(
            Integer stadiumId,
            Integer homeTeamId,
            Integer awayTeamId,
            Timestamp gameTime
    ) {
        String jpql = """
                    SELECT g FROM Game g
                    WHERE g.stadium.id = :stadiumId
                      AND g.homeTeam.id = :homeTeamId
                      AND g.awayTeam.id = :awayTeamId
                      AND g.gameTime = :gameTime
                """;

        List<Game> result = em.createQuery(jpql, Game.class)
                .setParameter("stadiumId", stadiumId)
                .setParameter("homeTeamId", homeTeamId)
                .setParameter("awayTeamId", awayTeamId)
                .setParameter("gameTime", gameTime)
                .getResultList();

        return result.stream().findFirst();
    }
}
