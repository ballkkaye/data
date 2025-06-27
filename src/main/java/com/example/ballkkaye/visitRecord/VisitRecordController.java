package com.example.ballkkaye.visitRecord;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VisitRecordController {
    private final VisitRecordService visitRecordService;
}