package com.example.ballkkaye.team.record.today;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayTeamRecordController {
    private final TodayTeamRecordService todayTeamRecordService;
}