package com.example.ballkkaye.player.hitterLineup;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HitterLineupController {
    private final HitterLineupService hitterLineUpService;

    //@Scheduled(cron = "0 */10 17-18 * * MON-FRI") // 평일 17:00 ~ 18:59 매 10분
    public void crawlHitterLineupsAndSaveWeekday() {
        hitterLineUpService.crawlHitterLineUpsAndSave();
    }


    @Scheduled(cron = "0 */10 13-17 * * SAT,SUN") // 주말 13:00 ~ 17:00 매 10분
    public void crawlHitterLineupsAndSaveWeekend() {
        hitterLineUpService.crawlHitterLineUpsAndSave();
    }


    @GetMapping("/admin/bot/hitter-lineup")
    public String crwalHitterLineUpAndSave() {
        hitterLineUpService.crawlHitterLineUpsAndSave();
        return "오늘 경기 타자 라인업 복사 완료";
    }
}