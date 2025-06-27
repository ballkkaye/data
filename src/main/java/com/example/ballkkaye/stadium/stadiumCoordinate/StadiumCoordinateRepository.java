package com.example.ballkkaye.stadium.stadiumCoordinate;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StadiumCoordinateRepository {
    private final EntityManager em;
}
