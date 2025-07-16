package com.example.ballkkaye.player.hitterLineup.today;

import com.example.ballkkaye.fcm.FcmService;
import com.example.ballkkaye.player.hitterLineup.HitterLineup;
import com.example.ballkkaye.player.hitterLineup.HitterLineupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodayHitterLineupService {
    private final TodayHitterLineupRepository todayHitterLineUpRepository;
    private final HitterLineupRepository hitterLineupRepository;
    private final FcmService fcmService;


    @Transactional
    public void copyTodayLineupFromHitterLineup() {
        LocalDate today = LocalDate.now();

        // 1. HitterLineup에서 오늘 날짜 라인업 전체 조회
        List<HitterLineup> todayLineups = hitterLineupRepository.findByGameDate(today);

        if (todayLineups.isEmpty()) {
            log.info("오늘 날짜의 라인업이 없습니다.");
            return;
        }

        // 2. 중복 제거하며 복사
        List<TodayHitterLineup> toSave = new ArrayList<>();
        for (HitterLineup h : todayLineups) {
            if (todayHitterLineUpRepository.existsByGameIdAndPlayerId(
                    h.getGame().getId(), h.getPlayer().getId())) {
                continue;
            }

            toSave.add(TodayHitterLineup.builder()
                    .game(h.getGame())
                    .team(h.getTeam())
                    .player(h.getPlayer())
                    .todayHitterOrder(h.getHitterOrder())
                    .position(h.getPosition())
                    .seasonAvg(h.getSeasonAvg())
                    .ab(h.getAb())
                    .h(h.getH())
                    .avg(h.getAvg())
                    .ops(h.getOps())
                    .build());
        }

        if (toSave.isEmpty()) {
            log.info("이미 모든 라인업이 저장되어 있습니다.");
            return;
        }

        todayHitterLineUpRepository.saveAll(toSave);
        log.info("TodayHitterLineup으로 %d개 복사 완료\n", toSave.size());
        // 알림
        fcmService.sendToUserRoleUsers("오늘의 타자 라인업이 공개되었습니다!");
    }
}
