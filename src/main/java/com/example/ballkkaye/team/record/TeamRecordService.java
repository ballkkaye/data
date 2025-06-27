package com.example.ballkkaye.team.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamRecordService {
    private final TeamRecordRepository teamRecordRepository;
}
