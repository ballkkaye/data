package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.fcm.FcmService;
import com.example.ballkkaye.player.startingPitcher.StartingPitcher;
import com.example.ballkkaye.player.startingPitcher.StartingPitcherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodayStartingPitcherService {
    private final TodayStartingPitcherRepository todayStartingPitcherRepository;
    private final StartingPitcherRepository startingPitcherRepository;
    private final FcmService fcmService;

    // 오늘 경기의 선발투수를 TodayStartingPitcher로 복사 후 저장
    @Transactional
    public void copyTodayStartingPitchers() {
        LocalDate today = LocalDate.now();
        Timestamp startOfDay = Timestamp.valueOf(today.atStartOfDay());
        Timestamp endOfDay = Timestamp.valueOf(today.plusDays(1).atStartOfDay());


        // [1] 오늘 날짜의 Game에 해당하는 선발투수 조회
        List<StartingPitcher> todayPitchers = startingPitcherRepository.findByGameDate(startOfDay, endOfDay);

        if (todayPitchers.isEmpty()) {
            log.warn("오늘 날짜({})의 선발투수 없음 → 복사 중단 (다음 주기 재시도)", today);
            return;
        }

        // [2] todayStartingPitcher 테이블에 기존 데이터가 있는지 확인
        boolean alreadyCopied = todayStartingPitcherRepository.existsAny();
        if (alreadyCopied) {
            log.info("TodayStartingPitcher 테이블에 이미 데이터가 존재함 → 중복 복사 방지");
            return;
        }


        // [3] 기존 todayStartingPitcher 데이터 초기화 (혹시나 남아있을 경우)
        todayStartingPitcherRepository.deleteAll();
        log.debug("기존 TodayStartingPitcher 테이블 데이터 초기화 완료");


        // [4] StartingPitcher → TodayStartingPitcher 로 변환 및 저장
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
        log.info("오늘({}) 선발투수 {}명 복사 완료", today, copied.size());

        // [5] 오늘 선발투수 복사 완료 후 이벤트 발행 (구독자 알림)
        fcmService.sendToUserRoleUsers("오늘의 승리예측이 업데이트 되었습니다!");
    }
}



