package com.example.ballkkaye.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    // 단기예보 insert
    @Scheduled(cron = "0 0 5 ? * TUE,WED,THU,FRI,SAT,SUN")
    public void scheduledShortForecast() {
        weatherService.getShortForecastAndSave();
    }

    // 단기예보 insert 후 copy
    @Scheduled(cron = "0 2 5 ? * TUE,WED,THU,FRI,SAT,SUN") // insert 완료 후 복사될 수 있도록 insert후  2분 뒤 실행
    public void scheduledCopyShortForecast() {
        weatherService.copyShortToUltra();
    }

    // 관리자용 단기 예보 insert
    @GetMapping("/s/admin/bot/getShortForecast")
    public void adminGetShortForecast() {
        weatherService.getShortForecastAndSave();
    }

    // copy 확인용
    @GetMapping("/s/admin/bot/getShortForecast2")
    public void adminCopyShortForecast() {
        weatherService.copyShortToUltra();
    }
}