package com.example.ballkkaye.weather.weatherUltra;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.weather.WeatherRequest;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

public class WeatherUltraRequest {

    @Data
    public static class SaveDTO {
        private Game game; // 경기
        private Stadium stadium; // 구장
        private List<WeatherRequest.SaveDTO.WeatherDTO> weatherDTOS;


        @Data
        public static class WeatherDTO {
            private Double temperature; // 기온
            private Double humidityPer;
            private Double windSpeed; // 풍속
            private WFCD weatherCode; // 풍향
            private WindDirection windDirection;  // 날씨코드
            private Timestamp forecastAt; // 예보 시각
            private Double rainAmount; // 강수량


            public WeatherDTO(Double temperature, Double humidityPer, Double windSpeed, WFCD weatherCode, WindDirection windDirection, Timestamp forecastAt, Double rainAmount) {
                this.temperature = temperature;
                this.humidityPer = humidityPer;
                this.windSpeed = windSpeed;
                this.weatherCode = weatherCode;
                this.windDirection = windDirection;
                this.forecastAt = forecastAt;
                this.rainAmount = rainAmount;

            }
        }


        public SaveDTO(Game game, Stadium stadium, List<WeatherRequest.SaveDTO.WeatherDTO> weatherDTOS) {
            this.game = game;
            this.stadium = stadium;
            this.weatherDTOS = weatherDTOS;
        }
    }
}
