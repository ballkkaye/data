package com.example.ballkkaye.player.hitterLineup.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayHitterLineupRepository {
    private final EntityManager em;


    /**
     * 오늘 타자 라인업 리스트를 모두 DB에 저장
     */
    public void saveAll(List<TodayHitterLineup> todayHitterLineups) {
        for (TodayHitterLineup h : todayHitterLineups) {
            em.persist(h);
        }
    }


    /**
     * 특정 게임에서 특정 선수가 이미 TodayHitterLineup에 존재하는지 여부 확인
     * - gameId, playerId 기준 중복 저장 방지 용도
     */
    public boolean existsByGameIdAndPlayerId(Integer gameId, Integer playerId) {
        Long count = em.createQuery("""
                        SELECT COUNT(t) FROM TodayHitterLineup t
                        WHERE t.game.id = :gameId AND t.player.id = :playerId
                        """, Long.class)
                .setParameter("gameId", gameId)
                .setParameter("playerId", playerId)
                .getSingleResult();

        return count > 0;
    }
}
