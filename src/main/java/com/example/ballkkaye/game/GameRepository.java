package com.example.ballkkaye.game;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

    public Game findById(Integer id) {
        return em.find(Game.class, id);
    }

    // 오늘 날짜의 경기 전체 조회 (gameId, gameTime, stadiumId 포함)
    public List<Game> findByToday() {
        try {
            String todayStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return em.createQuery("""
                                SELECT g
                                FROM Game g
                                WHERE FUNCTION('FORMATDATETIME', g.gameTime, 'yyyy-MM-dd') = :date
                            """, Game.class)
                    .setParameter("date", todayStr)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}