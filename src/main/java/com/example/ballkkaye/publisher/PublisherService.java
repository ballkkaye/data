package com.example.ballkkaye.publisher;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final StringRedisTemplate redisTemplate;

    // 오늘의 경기 이벤트 발행
    public void publishGameUpdatedEvent() {
        redisTemplate.convertAndSend("today-game-updated", "TODAY_GAME_UPDATED");
    }

    // 상대 전적 이벤트 발행
    public void publishHitterLineupUpdatedEvent() {
        redisTemplate.convertAndSend("hitter-lineup-updated", "TODAY_HITTER_LINEUP_UPDATED");
    }

    // 선발투수 라인업 이벤트 발행 - 승리예측
    public void publishStartingPitcherUpdatedEvent() {
        redisTemplate.convertAndSend("starting-pitcher-lineup-updated", "TODAY_STARTING_PITCHER_UPDATED");
    }

    // 팀기록 이벤트 발행
    public void publishTeamRecordUpdated() {
        redisTemplate.convertAndSend("team-record-update", "TEAM-RECORD-UPDATED");
    }

    // 필요하면 JSON 포맷도 가능
    public void publishAsJson(String channel, Object dto) {
        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            redisTemplate.convertAndSend(channel, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
    }
}
