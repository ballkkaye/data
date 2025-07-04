package com.example.ballkkaye.player.hitterLineup.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
     * 특정 날짜 기준으로 TodayHitterLineup 데이터가 존재하는지 여부 확인
     */
    public boolean existsByGameDate(LocalDate date) {
        Long count = em.createQuery("""
                        SELECT COUNT(t) FROM TodayHitterLineup t
                        WHERE DATE(t.game.gameTime) = :today
                        """, Long.class)
                .setParameter("today", date)
                .getSingleResult();

        return count > 0;
    }
}
