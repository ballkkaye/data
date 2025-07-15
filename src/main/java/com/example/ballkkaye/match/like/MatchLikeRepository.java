package com.example.ballkkaye.match.like;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MatchLikeRepository {
    private final EntityManager em;
}
