package com.example.ballkkaye.visitRecord.Image;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VisitRecordImageRepository {
    private final EntityManager em;
}
