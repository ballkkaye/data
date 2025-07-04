package com.example.ballkkaye.player.hitterLineup;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HitterLineupController {
    private final HitterLineupService hitterLineUpService;


    //@Scheduled(cron = "0 */10 12-19 * * *")  // 매일 12시~19시 사이 10분마다 실행
    public void crawlHitterLineupsAndSaveWeekend() {
        hitterLineUpService.crawlHitterLineUpsByGameTime();
    }


    @GetMapping("/admin/bot/hitter-lineup")
    public String crawlHitterLineUpsByGameTime() {
        hitterLineUpService.crawlHitterLineUpsByGameTime();
        return "크롤링 완료";
    }
}