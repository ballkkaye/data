package com.example.ballkkaye.game.today;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayGameRepository {

    private final EntityManager em;

    public void deleteAll() {
        em.createQuery("delete from TodayGame").executeUpdate();
    }

    public void save(List<TodayGame> games) {
        for (TodayGame game : games) {
            em.persist(game);
        }
    }


    /**
     * stadiumId와 gameTime이 일치하는 경기 데이터가 today_game_tb에 존재하는지 확인
     */
    public boolean existsByStadiumIdAndGameTime(Integer stadiumId, Timestamp gameTime) {
        String jpql = """
                    SELECT COUNT(t)
                    FROM TodayGame t
                    WHERE t.stadium.id = :stadiumId
                      AND t.gameTime = :gameTime
                """;

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("stadiumId", stadiumId)
                .setParameter("gameTime", gameTime)
                .getSingleResult();

        return count > 0;
    }


    /**
     * 홈팀 ID와 원정팀 ID를 기반으로 오늘 경기(TodayGame)의 게임 ID 목록을 조회
     * - 오늘 날짜에 해당하는 경기를 TodayGame 테이블에서 조회
     */
    public List<Integer> findByTeamId(Integer homeTeamId, Integer awayTeamId) {
        return em.createQuery("""
                            SELECT g.id
                            FROM TodayGame g
                            WHERE g.homeTeam.id = :homeTeamId
                              AND g.awayTeam.id = :awayTeamId
                        """, Integer.class)
                .setParameter("homeTeamId", homeTeamId)
                .setParameter("awayTeamId", awayTeamId)
                .getResultList();
    }

}