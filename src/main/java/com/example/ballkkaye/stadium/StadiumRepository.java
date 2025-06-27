package com.example.ballkkaye.stadium;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StadiumRepository {

    private final EntityManager em;

    public Stadium findById(Integer stadiumId) {
        return em.find(Stadium.class, stadiumId);
    }
}
