package com.example.ballkkaye.weather.weatherUltra;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherUltraController {
    private final WeatherUltraService weatherUltraService;
}
