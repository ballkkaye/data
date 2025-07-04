package com.example.ballkkaye.player.hitterLineup.today;

import com.example.ballkkaye.player.hitterLineup.HitterLineup;
import com.example.ballkkaye.player.hitterLineup.HitterLineupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayHitterLineupService {
    private final TodayHitterLineupRepository todayHitterLineUpRepository;
    private final HitterLineupRepository hitterLineupRepository;


    @Transactional
    public void copyTodayLineupFromHitterLineup() {
        LocalDate today = LocalDate.now();

        // 1. 이미 TodayHitterLineup에 오늘 날짜 라인업이 있으면 skip
        boolean exists = todayHitterLineUpRepository.existsByGameDate(today);
        if (exists) {
            System.out.println("⚠️ 이미 오늘 날짜 라인업이 존재합니다. 복사하지 않습니다.");
            return;
        }

        // 2. HitterLineup에서 오늘 날짜 라인업 조회 (game.gameTime 기준)
        List<HitterLineup> todayLineups = hitterLineupRepository.findByGameDate(today);

        if (todayLineups.isEmpty()) {
            System.out.println("❌ 오늘 날짜의 라인업이 없습니다.");
            return;
        }

        // 3. 변환 및 저장
        List<TodayHitterLineup> toSave = todayLineups.stream().map(h -> TodayHitterLineup.builder()
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
                .build()
        ).toList();

        todayHitterLineUpRepository.saveAll(toSave);
        System.out.printf("✅ TodayHitterLineup으로 %d개 복사 완료\n", toSave.size());
    }
}
