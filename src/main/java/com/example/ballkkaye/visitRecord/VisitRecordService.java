package com.example.ballkkaye.visitRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VisitRecordService {
    private final VisitRecordRepository visitRecordRepository;
}
