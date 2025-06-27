package com.example.ballkkaye.stadium.stadiumCorrection;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StadiumCorrectionRepository {
    private final EntityManager em;
}
