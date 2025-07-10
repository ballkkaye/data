package com.example.ballkkaye.stadium.stadiumCorrection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StadiumCorrectionRepository {
    private final EntityManager em;

    public double getCorrectionByStadiumIdAndYear(Integer stadiumId, int year) {
        try {
            return em.createQuery("""
                                SELECT s.correction FROM StadiumCorrection s
                                WHERE s.stadium.id = :stadiumId AND s.thisYear = :year
                            """, Double.class)
                    .setParameter("stadiumId", stadiumId)
                    .setParameter("year", year)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 1.0; // 기본값 (필요에 따라 바꿔도 됨)
        }
    }
}
