package com.example.ballkkaye.stadium.stadiumCorrection;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StadiumCorrectionRepository {
    private final EntityManager em;

    public Double getCorrectionByStadiumIdAndYear(Integer stadiumId, int year) {
        return em.createQuery(
                        "SELECT s.correction FROM StadiumCorrection s " +
                                "WHERE s.stadium.id = :stadiumId AND s.thisYear = :year", Double.class)
                .setParameter("stadiumId", stadiumId)
                .setParameter("year", year)
                .getSingleResult();
    }
}
