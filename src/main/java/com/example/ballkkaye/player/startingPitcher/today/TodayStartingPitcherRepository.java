package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TodayStartingPitcherRepository {
    private final EntityManager em;


    public boolean existsAny() {
        Long count = em.createQuery("SELECT COUNT(t) FROM TodayStartingPitcher t", Long.class)
                .getSingleResult();
        return count > 0;
    }

    public void deleteAll() {
        em.createQuery("DELETE FROM TodayStartingPitcher").executeUpdate();
    }

    public void saveAll(List<TodayStartingPitcher> pitchers) {
        for (TodayStartingPitcher p : pitchers) {
            em.persist(p);
        }
    }

    public Optional<Double> getPitcherEraByGameAndTeam(Game game, Team team) {
        try {
            Double era = em.createQuery("""
                                SELECT t.ERA FROM TodayStartingPitcher t
                                WHERE t.game = :game AND t.player.team = :team
                            """, Double.class)
                    .setParameter("game", game)
                    .setParameter("team", team)
                    .getSingleResult();
            return Optional.ofNullable(era);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<String> findByGameIdAndTeam(Integer gameId, String teamName) {
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
}
