package com.example.ballkkaye.user.userMatch;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserMatchRepository {
    private final EntityManager em;
}
