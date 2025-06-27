package com.example.ballkkaye.team.record;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TeamRecordRepository {
    private final EntityManager em;
}
