package com.example.ballkkaye.team.record.today;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodayTeamRecordController {
    private final TodayTeamRecordService todayTeamRecordService;

    // TodayTeamRecord 테이블 삭제 후 갱신
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    // << TeamRecordService에서 TeamRecord 저장 후 TodayTeamRecord 까지 업데이트해주는 로직이 묶여있어서 현재는 비활성화
    public void scheduledUpdate() {
        todayTeamRecordService.updateBot();
    }

    // TodayTeamRecord 테이블 삭제 후 갱신
    // 관리자용 오늘의 팀 기록 갱신
    @GetMapping("/s/admin/todayteamrecord")
    public void adminUpdate() {
        todayTeamRecordService.updateBot();
    }
}