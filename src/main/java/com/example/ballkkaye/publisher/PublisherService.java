package com.example.ballkkaye.publisher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService {

    private final StringRedisTemplate redisTemplate;

    // 오늘의 경기 이벤트 발행
    public void publishGameUpdatedEvent() {
        try {
            redisTemplate.convertAndSend("today-game-updated", "TODAY_GAME_UPDATED");
        } catch (Exception e) {
            log.warn("Redis publishGameUpdatedEvent 실패: {}", e.getMessage());
        }
    }

    // 상대 전적 이벤트 발행
    public void publishHitterLineupUpdatedEvent() {
        try {
            redisTemplate.convertAndSend("hitter-lineup-updated", "TODAY_HITTER_LINEUP_UPDATED");
        } catch (Exception e) {
            log.warn("Redis publishGameUpdatedEvent 실패: {}", e.getMessage());
        }
    }

    // 선발투수 라인업 이벤트 발행 - 승리예측
    public void publishStartingPitcherUpdatedEvent() {
        try {
            redisTemplate.convertAndSend("starting-pitcher-lineup-updated", "TODAY_STARTING_PITCHER_UPDATED");
        } catch (Exception e) {
            log.warn("Redis publishGameUpdatedEvent 실패: {}", e.getMessage());
        }
    }

    // 팀기록 이벤트 발행
    public void publishTeamRecordUpdated() {
        try {
            redisTemplate.convertAndSend("team-record-update", "TEAM-RECORD-UPDATED");
        } catch (Exception e) {
            log.warn("Redis publishGameUpdatedEvent 실패: {}", e.getMessage());
        }
    }

}
