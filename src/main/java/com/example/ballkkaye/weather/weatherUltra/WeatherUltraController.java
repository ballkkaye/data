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
    public void getUltraWeather() {
        weatherUltraService.getUltraForecastAndSave();
    }

    @GetMapping("/admin/bot/getUltraForecastAndSave")
    public String getUltraForecastAndSave() {
        weatherUltraService.getUltraForecastAndSave();
        return "크롤링 완료"; // TODO: 관리자 페이지 만들어지면 수정
    }
}
