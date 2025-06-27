package com.example.ballkkaye.user.userPrediction;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserPredictionRepository {
    private final EntityManager em;
}
