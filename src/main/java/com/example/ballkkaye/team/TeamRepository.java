package com.example.ballkkaye.team;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TeamRepository {
    
    private final EntityManager em;

    public Team findById(Integer teamId) {
        return em.find(Team.class, teamId);
    }
}
