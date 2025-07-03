package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.player.startingPitcher.StartingPitcher;
import com.example.ballkkaye.player.startingPitcher.StartingPitcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayStartingPitcherService {
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;
    private final StartingPitcherRepository startingPitcherRepository;

    @Transactional
    public void copyTodayStartingPitchers() {
        LocalDate today = LocalDate.now();
        Timestamp startOfDay = Timestamp.valueOf(today.atStartOfDay());
        Timestamp endOfDay = Timestamp.valueOf(today.plusDays(1).atStartOfDay());


        // 1. 오늘 날짜의 선발투수가 존재하는지 확인
        List<StartingPitcher> todayPitchers = startingPitcherRepository.findByGameDate(startOfDay, endOfDay);

        if (todayPitchers.isEmpty()) {
            System.out.println("선발투수 없음 → 다음 주기 재시도"); // TODO: 로그 관리
            return;
        }

        // 2. today 테이블에 이미 복사된 선발투수가 있는지 확인
        boolean alreadyCopied = todayStartingPitcherRepository.existsAny();
        if (alreadyCopied) {
            return;
        }


        // 3. 기존 today 테이블 초기화 (혹시나 있을 경우)
        todayStartingPitcherRepository.deleteAll();


        // 4. StartingPitcher → TodayStartingPitcher 변환 및 저장
        List<TodayStartingPitcher> copied = todayPitchers.stream()
                .map(p -> TodayStartingPitcher.builder()
                        .game(p.getGame())
                        .player(p.getPlayer())
                        .profileUrl(p.getProfileUrl())
                        .ERA(p.getERA())
                        .gameCount(p.getGameCount())
                        .result(p.getResult())
                        .QS(p.getQS())
                        .WHIP(p.getWHIP())
                        .build())
                .toList();

        todayStartingPitcherRepository.saveAll(copied);
        System.out.println("오늘 선발투수 " + copied.size() + "명 복사 완료");  // TODO: 로그 관리
    }
}



