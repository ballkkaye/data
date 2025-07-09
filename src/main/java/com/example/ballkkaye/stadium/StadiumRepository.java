package com.example.ballkkaye.stadium;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StadiumRepository {

    private final EntityManager em;

    public Optional<Stadium> findById(Integer id) {
        try {
            Stadium stadiumPS = em.createQuery("select s from Stadium s where s.id = :id", Stadium.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(stadiumPS);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }
}
