package com.example.ballkkaye.weather.weatherUltra;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherUltraController {
    private final WeatherUltraService weatherUltraService;


    /**
     * 화요일~일요일 + 요일마다 13~18시 + 10분 간격 실행
     * 평일, 주말마다 유동적인 경기시간 & 더블헤더에 대응
     */
    // 초단기예보 insert
    @Scheduled(cron = "0 */10 13-18 ? * *")
    public void scheduledGetUltraWeather() {
        weatherUltraService.getUltraForecastAndSave();
    }

    // 관리자용 초단기 예보 insert
    @GetMapping("/admin/bot/getUltraForecastAndSave")
    public void adminGetUltraForecastAndSave() {
        weatherUltraService.getUltraForecastAndSave();
    }
}
