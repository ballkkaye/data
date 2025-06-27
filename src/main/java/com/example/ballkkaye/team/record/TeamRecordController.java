package com.example.ballkkaye.team.record;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamRecordController {
    private final TeamRecordService teamRecordService;
}