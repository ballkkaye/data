package com.example.ballkkaye.stadium.stadiumCorrection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StadiumCorrectionRepository {
    private final EntityManager em;

    public Optional<Double> getCorrectionByStadiumIdAndYear(Integer stadiumId, int year) {
        try {
            Double correction = em.createQuery("""
                                SELECT s.correction FROM StadiumCorrection s
                                WHERE s.stadium.id = :stadiumId AND s.thisYear = :year
                            """, Double.class)
                    .setParameter("stadiumId", stadiumId)
                    .setParameter("year", year)
                    .getSingleResult();
            return Optional.ofNullable(correction);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
