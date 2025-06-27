package com.example.ballkkaye.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
}
