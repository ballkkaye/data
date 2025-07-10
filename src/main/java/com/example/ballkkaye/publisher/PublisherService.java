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
    public void publishGameUpdated(Integer gameId) {
        redisTemplate.convertAndSend("today-game-updated", gameId.toString());
    }

    public void publishHitterLineupUpdated(Integer gameId) {
        redisTemplate.convertAndSend("hitter-lineup-updated", gameId.toString());
    }

    public void publishWeatherAlert(Integer stadiumId, String message) {
        String payload = String.format("%d:%s", stadiumId, message);
        redisTemplate.convertAndSend("weather-alert", payload);
    }

    public void publishTeamRecordUpdated(Integer teamId, String teamName) {
        String payload = String.format("%d:%s", teamId, teamName);
        redisTemplate.convertAndSend("team-record-updated", payload);
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
