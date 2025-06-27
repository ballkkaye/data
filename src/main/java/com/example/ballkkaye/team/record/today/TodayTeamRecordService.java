package com.example.ballkkaye.team.record.today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodayTeamRecordService {
    private final TodayTeamRecordRepository todayTeamRecordRepository;
}
