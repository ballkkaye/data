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
    public void cronShortForecast() {
        weatherService.getShortForecastAndSave();
    }

    // 단기예보 insert 후 copy
    @Scheduled(cron = "0 2 5 ? * TUE,WED,THU,FRI,SAT,SUN") // insert 완료 후 복사될 수 있도록 insert후  2분 뒤 실행
    public void cronCopyShortForecast() {
        weatherService.copyShortToUltra();
    }

    // 관리자용 - role: admin / bot: 자동화
    @GetMapping("/admin/bot/getShortForecast")
    public String getShortForecast() {
        weatherService.getShortForecastAndSave();
        return "크롤링 완료"; // TODO: 관리자 페이지 만들어지면 수정
    }


    // copy 확인용
    @GetMapping("/admin/bot/getShortForecast2")
    public String copyShortForecast() {
        weatherService.copyShortToUltra();
        return "크롤링 완료"; // TODO: 관리자 페이지 만들어지면 수정
    }
}