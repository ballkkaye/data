package com.example.ballkkaye.game.today;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TodayGameRepository {

    private final EntityManager em;

    // 새로운 TodayGame 엔티티를 영속화 (저장)
    public void save(TodayGame todayGame) {
        em.persist(todayGame);
    }

    // 기존 TodayGame 엔티티를 영속화 컨텍스트에 병합 (업데이트)
    public TodayGame update(TodayGame todayGame) {
        return em.merge(todayGame);
    }

    // gameId를 기준으로 TodayGame을 찾음
    public Optional<TodayGame> findByGameId(Integer gameId) {
        try {
            TodayGame todayGame = em.createQuery(
                            "SELECT t FROM TodayGame t WHERE t.game.id = :gameId", TodayGame.class)
                    .setParameter("gameId", gameId)
                    .getSingleResult();
            return Optional.of(todayGame);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // 모든 TodayGame을 조회
    public List<TodayGame> findAll() {
        return em.createQuery("SELECT t FROM TodayGame t", TodayGame.class).getResultList();
    }

}