package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TodayStartingPitcherRepository {
    private final EntityManager em;


    // 오늘 선발투수 데이터 존재 여부 확인
    public boolean existsAny() {
        Long count = em.createQuery("SELECT COUNT(t) FROM TodayStartingPitcher t", Long.class)
                .getSingleResult();
        return count > 0;
    }


    // 기존 선발투수 데이터 삭제
    public void deleteAll() {
        em.createQuery("DELETE FROM TodayStartingPitcher").executeUpdate();
    }


    // 오늘 선발 투수 일괄 저장
    public void saveAll(List<TodayStartingPitcher> pitchers) {
        for (TodayStartingPitcher p : pitchers) {
            em.persist(p);
        }
    }


    // 특정 경기와 팀에 해당하는 선발투수의 ERA 조회
    public Double findPitcherEraByGameAndTeam(Game game, Team team) {
        try {
            return em.createQuery("""
                                SELECT t.ERA FROM TodayStartingPitcher t
                                WHERE t.game = :game AND t.player.team = :team
                            """, Double.class)
                    .setParameter("game", game)
                    .setParameter("team", team)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    // 게임 ID와 팀명 기준으로 선발투수 이름 조회
    public List<String> findByGameIdAndTeamName(Integer gameId, String teamName) {
        return em.createQuery("""
                        SELECT sp.player.name
                        FROM TodayStartingPitcher sp
                        WHERE sp.game.id = :gameId 
                          AND sp.player.team.teamName LIKE CONCAT(:teamName, '%')
                        """, String.class)
                .setParameter("gameId", gameId)
                .setParameter("teamName", teamName)
                .getResultList();
    }

    // 오늘의 선발투수 목록 조회
    public List<TodayStartingPitcher> findAll() {
        return em.createQuery("SELECT t FROM TodayStartingPitcher t", TodayStartingPitcher.class)
                .getResultList();
    }
}
