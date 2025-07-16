package com.example.ballkkaye.match;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MatchRepository {
    private final EntityManager em;
}
