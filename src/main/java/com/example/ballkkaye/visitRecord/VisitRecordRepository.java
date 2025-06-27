package com.example.ballkkaye.visitRecord;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VisitRecordRepository {
    private final EntityManager em;
}
