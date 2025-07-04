package com.example.ballkkaye.player;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Player> findByKboPlayerId(Integer kboPlayerId) {
        try {
            Player player = em.createQuery("SELECT p FROM Player p WHERE p.kboPlayerId = :kboPlayerId", Player.class)
                    .setParameter("kboPlayerId", kboPlayerId)
                    .getSingleResult();
            return Optional.of(player);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public Optional<Player> findByNameAndTeamId(String name, Integer teamId) {
        try {
            Player player = em.createQuery("""
                            SELECT p FROM Player p
                            WHERE p.name = :name AND p.team.id = :teamId
                            """, Player.class)
                    .setParameter("name", name)
                    .setParameter("teamId", teamId)
                    .getSingleResult();
            return Optional.of(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
