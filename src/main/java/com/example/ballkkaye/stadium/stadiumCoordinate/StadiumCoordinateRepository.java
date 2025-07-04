package com.example.ballkkaye.stadium.stadiumCoordinate;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StadiumCoordinateRepository {
    private final EntityManager em;

    public Optional<StadiumCoordinate> findByStadiumId(Integer stadiumId) {
        try {
            StadiumCoordinate coordinate = em.find(StadiumCoordinate.class, stadiumId);
            return Optional.ofNullable(coordinate);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
