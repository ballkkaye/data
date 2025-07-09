package com.example.ballkkaye.user.userPrediction;

import com.example.ballkkaye.game.Game;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserPredictionRepository {
    private final EntityManager em;

    public List<UserPrediction> findByGame(Game game) {
        String jpql = "SELECT up FROM UserPrediction up WHERE up.game = :game";
        return em.createQuery(jpql, UserPrediction.class)
                .setParameter("game", game)
                .getResultList();
    }


}
