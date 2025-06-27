package com.example.ballkkaye.player;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PlayerRepository {
    private final EntityManager em;

    // 선수 목록 저장(player_tb)
    public void saveAll(List<Player> players) {
        for (Player player : players) {
            em.persist(player);
        }
    }
}
