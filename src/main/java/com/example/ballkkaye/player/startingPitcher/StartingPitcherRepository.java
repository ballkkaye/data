package com.example.ballkkaye.player.startingPitcher;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class StartingPitcherRepository {
    private final EntityManager em;

    // 선발 투수 저장
    public void saveAll(List<StartingPitcher> entities) {
        for (StartingPitcher entity : entities) {
            em.persist(entity);
        }
    }


    // 게임 시간 기준으로 오늘 날짜에 해당하는 선발투수 전체 조회
    public List<StartingPitcher> findByGameDate(Timestamp startOfDay, Timestamp endOfDay) {
        return em.createQuery("""
                            SELECT s
                            FROM StartingPitcher s
                            WHERE s.game.gameTime >= :startOfDay
                              AND s.game.gameTime < :endOfDay
                        """, StartingPitcher.class)
                .setParameter("startOfDay", startOfDay)
                .setParameter("endOfDay", endOfDay)
                .getResultList();
    }

}
