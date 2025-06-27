package com.example.ballkkaye.visitRecord.Image;

import com.example.ballkkaye.visitRecord.VisitRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VisitRecordImageController {
    private final VisitRecordService visitRecordService;
}