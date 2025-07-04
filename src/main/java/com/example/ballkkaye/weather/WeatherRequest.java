package com.example.ballkkaye.weather;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

public class WeatherRequest {

    @Data
    public static class SaveDTO {
        private Integer gameId; // 경기id
        private Integer stadiumId; // 구장id
        private List<WeatherDTO> weatherDTOS;


        @Data
        public static class WeatherDTO {
            private Double temperature; // 기온
            private Double humidityPer;
            private Double windSpeed; // 풍속
            private WFCD weatherCode; // 풍향
            private WindDirection windDirection;  // 날씨코드
            private Double rainPer; //  강수확률
            private Timestamp forecastAt; // 예보 시각
            private Double rainAmount; // 강수량


            public WeatherDTO(Double temperature, Double humidityPer, Double windSpeed, WFCD weatherCode, WindDirection windDirection, Double rainPer, Timestamp forecastAt, Double rainAmount) {
                this.temperature = temperature;
                this.humidityPer = humidityPer;
                this.windSpeed = windSpeed;
                this.weatherCode = weatherCode;
                this.windDirection = windDirection;
                this.rainPer = rainPer;
                this.forecastAt = forecastAt;
                this.rainAmount = rainAmount;
            }
        }

        public SaveDTO(Integer gameId, Integer stadiumId, List<WeatherDTO> weatherDTOS) {
            this.gameId = gameId;
            this.stadiumId = stadiumId;
            this.weatherDTOS = weatherDTOS;
        }

    }
}
