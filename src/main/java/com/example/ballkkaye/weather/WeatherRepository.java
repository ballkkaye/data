package com.example.ballkkaye.weather;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WeatherRepository {
    private final EntityManager em;

    // 특정 게임 ID와 예보 날짜(LocalDate 기준)를 조건으로 Weather 데이터를 조회
    public List<Weather> findByGameIdAndDate(Integer gameId, LocalDate date) {
        Timestamp start = Timestamp.valueOf(date.atStartOfDay());
        Timestamp end = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

        return em.createQuery("""
                            SELECT w FROM Weather w
                            WHERE w.game.id = :gameId
                              AND w.forecastAt >= :start
                              AND w.forecastAt < :end
                        """, Weather.class)
                .setParameter("gameId", gameId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public void saveAll(List<Weather> weatherList) {
        for (Weather entity : weatherList) {
            em.persist(entity);
        }
    }
}
