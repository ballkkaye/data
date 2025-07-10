package com.example.ballkkaye.player.hitterLineup;

import com.example.ballkkaye.game.Game;
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
public class HitterLineupRepository {
    private final EntityManager em;


    /**
     * 타자 라인업 리스트를 DB에 일괄 저장
     */
    public void saveAll(List<HitterLineup> hitterLineups) {
        for (HitterLineup h : hitterLineups) {
            em.persist(h);
        }
    }


    /**
     * 모든 타자 라인업과 해당 경기의 gameTime을 함께 조회
     * - 복사 또는 필터링(날짜 기준) 용도로 사용됨
     */
    public List<Object[]> findAllWithGameIdAndGameTime() {
        return em.createQuery("""
                    SELECT h, h.game.gameTime
                    FROM HitterLineup h
                """, Object[].class).getResultList();
    }


    /**
     * 특정 경기에서 특정 선수가 이미 저장되어 있는지 여부 확인
     * - 중복 저장 방지를 위한 검증 용도로 사용
     */
    public boolean existsByGameIdAndPlayerId(Integer gameId, Integer playerId) {
        Long count = em.createQuery("""
                            SELECT COUNT(h)
                            FROM HitterLineup h
                            WHERE h.game.id = :gameId AND h.player.id = :playerId
                        """, Long.class)
                .setParameter("gameId", gameId)
                .setParameter("playerId", playerId)
                .getSingleResult();

        return count > 0;
    }


    /**
     * 특정 날짜에 해당하는 경기(Game)에 출전한 타자 라인업(HitterLineUp) 목록 조회
     * Game 엔티티의 gameTime 필드를 기준으로 해당 날짜에 해당하는 라인업만 필터링
     * DATE 함수는 DBMS에서 날짜 비교를 위해 사용됨 (시간은 무시)
     */
    public List<HitterLineup> findByGameTime(LocalDate targetDate) {
        String dateStr = targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return em.createQuery("""
                            SELECT h
                            FROM HitterLineup h
                            WHERE FUNCTION('FORMATDATETIME', h.game.gameTime, 'yyyy-MM-dd') = :targetDate
                        """, HitterLineup.class)
                .setParameter("targetDate", dateStr)
                .getResultList();
    }


    /**
     * 모든 타자 라인업 정보에서 경기 ID, 선수 이름, 팀 이름, 홈/어웨이 팀 ID를 추출하여 반환
     * - 타자 이름과 그 선수가 속한 팀 이름까지 포함
     * - 주로 라인업 복사 또는 매칭 비교 등에 활용
     */
    public List<Object[]> findAllWithPlayerTeamNameAndGameId() {
        return em.createQuery("""
                    SELECT h.game.id, h.player.name, h.player.team.teamName,
                           h.game.homeTeam.id, h.game.awayTeam.id
                    FROM HitterLineup h
                """, Object[].class).getResultList();
    }


    /**
     * 오늘 날짜의 경기 기준으로 타자 라인업 전체 조회
     * - Game 엔티티의 gameTime 날짜 필드 기준
     * - 라인업 복사 시, 기준 데이터를 추출하는 용도로 사용
     */
    public List<HitterLineup> findByGameDate(LocalDate today) {
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 다음 날 00시

        return em.createQuery("""
                        SELECT h FROM HitterLineup h
                        JOIN h.game g
                        WHERE g.gameTime BETWEEN :start AND :end
                        """, HitterLineup.class)
                .setParameter("start", Timestamp.valueOf(startOfDay))
                .setParameter("end", Timestamp.valueOf(endOfDay))
                .getResultList();
    }

    /**
     * 특정 경기(Game)에 해당하는 라인업이 이미 존재하는지 확인
     * - 중복 저장을 방지하기 위한 용도로 사용됨
     * - 해당 경기 ID에 대한 라인업이 하나라도 존재하면 true 반환
     */
    public boolean existsByGame(Game game) {
        Long count = em.createQuery("""
                            SELECT COUNT(h)
                            FROM HitterLineup h
                            WHERE h.game = :game
                        """, Long.class)
                .setParameter("game", game)
                .getSingleResult();

        return count > 0;
    }
}
