package com.example.ballkkaye.team.record.today;

import com.example.ballkkaye.fcm.FcmService;
import com.example.ballkkaye.team.record.TeamRecord;
import com.example.ballkkaye.team.record.TeamRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodayTeamRecordService {
    private final TodayTeamRecordRepository todayTeamRecordRepository;
    private final TeamRecordRepository teamRecordRepository;
    private final FcmService fcmService;

    // TodayTeamRecord 테이블 삭제 후 갱신
    @Transactional
    public void updateBot() {
        todayTeamRecordRepository.deleteAll();

        List<TeamRecord> teamRecords = teamRecordRepository.findLatest10();

        List<TodayTeamRecord> todayTeamRecords = new ArrayList<>();
        for (TeamRecord tr : teamRecords) {
            TodayTeamRecordRequest.saveDto dto = new TodayTeamRecordRequest.saveDto(tr);
            TodayTeamRecord todayTeamRecord = dto.toEntity(tr.getTeam());
            todayTeamRecords.add(todayTeamRecord);
        }

        todayTeamRecordRepository.save(todayTeamRecords);
        // 알림
        fcmService.sendToUserRoleUsers("팀 기록이 업데이트되었습니다!");
    }
}
