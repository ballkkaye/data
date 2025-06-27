package com.example.ballkkaye.team.record;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamRecordController {

    private final TeamRecordService teamRecordService;

    // 팀 기록 저장
    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    public void saveBot() {
        teamRecordService.saveBot();
    }

    // 관라지용 팀 기록 저장
    @GetMapping("/s/admin/bot/teamrecord")
    public void adminSaveBot() {
        teamRecordService.saveBot();
    }

    // 팀 기록 저장 + 오늘의 팀 기록 갱신
    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    public void saveAndRefresh() {
        teamRecordService.saveAndRefresh();
    }

    // 관라지용 팀 기록 저장 + 오늘의 팀 기록 갱신 저장  TODO
    @GetMapping("/s/admin/bot/teamrecord/sync")
    public void adminSaveAndRefresh() {
        teamRecordService.saveAndRefresh();
    }
}