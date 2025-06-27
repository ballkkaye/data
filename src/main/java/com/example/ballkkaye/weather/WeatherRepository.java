package com.example.ballkkaye.weather;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WeatherRepository {
    private final EntityManager em;
}
