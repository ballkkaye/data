package com.example.ballkkaye.weather.weatherUltra;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import com.example.ballkkaye.weather.WeatherRequest;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

public class WeatherUltraRequest {

    @Data
    public static class SaveDTO {
        private Integer gameId; // 경기id
        private Integer stadiumId; // 구장id
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
            private Double rainoutPer;  // 경기취소 예측 확률


            public WeatherDTO(Double temperature, Double humidityPer, Double windSpeed, WFCD weatherCode, WindDirection windDirection, Timestamp forecastAt, Double rainAmount, Double rainoutPer) {
                this.temperature = temperature;
                this.humidityPer = humidityPer;
                this.windSpeed = windSpeed;
                this.weatherCode = weatherCode;
                this.windDirection = windDirection;
                this.forecastAt = forecastAt;
                this.rainAmount = rainAmount;
                this.rainoutPer = rainoutPer;
            }
        }

        public SaveDTO(Integer gameId, Integer stadiumId, List<WeatherRequest.SaveDTO.WeatherDTO> weatherDTOS) {
            this.gameId = gameId;
            this.stadiumId = stadiumId;
            this.weatherDTOS = weatherDTOS;
        }

    }
}
