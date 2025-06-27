package com.example.ballkkaye.weather.weatherUltra;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WeatherUltraRepository {

    private final EntityManager em;

    /**
     * 초단기예보(WeatherUltra) 데이터를 일괄 저장
     */
    public void saveAll(List<WeatherUltra> weatherUltraList) {
        for (WeatherUltra entity : weatherUltraList) {
            em.persist(entity);
        }
    }


    /**
     * 특정 구장의 예보 시각이 오늘(today)이고, 주어진 시간 범위(start~end)에 해당하는 예보 조회
     * 사용 목적:
     * - 초단기예보 저장 시, 기존 단기예보의 rainPer 값을 참조하기 위해 조회
     */
    public List<WeatherUltra> findByStadiumIdAndForecastDateRange(
            Integer stadiumId, LocalDate today, Timestamp start, Timestamp end) {

        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        return em.createQuery("""
                        SELECT w
                        FROM WeatherUltra w
                        WHERE w.stadiumId = :stadiumId
                          AND w.forecastAt BETWEEN :startOfDay AND :endOfDay
                          AND w.forecastAt BETWEEN :start AND :end
                        """, WeatherUltra.class)
                .setParameter("stadiumId", stadiumId)
                .setParameter("startOfDay", Timestamp.valueOf(startOfDay))
                .setParameter("endOfDay", Timestamp.valueOf(endOfDay))
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }


    /**
     * update된 초단기예보 데이터만 저장
     */
    public void save(WeatherUltra entity) {
        em.persist(entity);
    }


    /**
     * 주어진 gameId, stadiumId, forecastAt 시각을 기준으로
     * ±1분 내의 WeatherUltra 데이터를 조회한다.
     * 주로 중복 여부 확인 또는 업데이트 대상 조회에 사용됨.
     */
    public WeatherUltra findByGameIdAndStadiumIdAndForecastAtNearTime(Integer gameId, Integer stadiumId, Timestamp forecastAt) {
        List<WeatherUltra> result = em.createQuery("""
                        SELECT w FROM WeatherUltra w
                        WHERE w.gameId = :gameId
                          AND w.stadiumId = :stadiumId
                          AND w.forecastAt BETWEEN :startTime AND :endTime
                        """, WeatherUltra.class)
                // [1] 매개변수 바인딩: 경기 ID, 구장 ID, 시간 범위 (±1분)
                .setParameter("gameId", gameId)
                .setParameter("stadiumId", stadiumId)
                .setParameter("startTime", Timestamp.valueOf(forecastAt.toLocalDateTime().minusMinutes(1)))
                .setParameter("endTime", Timestamp.valueOf(forecastAt.toLocalDateTime().plusMinutes(1)))
                .getResultList();

        // [2] 결과가 존재하면 첫 번째 결과 반환, 없으면 null 반환
        return result.isEmpty() ? null : result.get(0);
    }


}
