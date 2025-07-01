package com.example.ballkkaye._core.util;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import com.example.ballkkaye.weather.WeatherRequest;
import com.example.ballkkaye.weather.weatherUltra.WeatherUltraRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static class GridXY {
        public final int nx;
        public final int ny;

        public GridXY(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }

        @Override
        public String toString() {
            return "nx=" + nx + ", ny=" + ny;
        }
    }

    /**
     * 위도(latitude), 경도(longitude)를 기상청 단기/초단기 예보용 격자 좌표로 변환
     *
     * @param lat 위도 (예: 37.5665)
     * @param lon 경도 (예: 126.9780)
     * @return 격자 좌표 (nx, ny)
     */
    public static GridXY convertToGrid(double lat, double lon) {
        double RE = 6371.00877;  // 지구 반지름(km)
        double GRID = 5.0;       // 격자 간격(km)
        double SLAT1 = 30.0;     // 투영 위도1
        double SLAT2 = 60.0;     // 투영 위도2
        double OLON = 126.0;     // 기준 경도
        double OLAT = 38.0;      // 기준 위도
        double XO = 43;          // 기준점 X좌표
        double YO = 136;         // 기준점 Y좌표

        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.pow(Math.tan(Math.PI * 0.25 + slat1 * 0.5), sn) * Math.cos(slat1) / sn;
        double ro = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);

        double ra = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5), sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return new GridXY(nx, ny);
    }


    /**
     * 풍향 각도를 WindDirection Enum으로 변환 (0~360도 기준)
     */
    public static WindDirection mapVecToDirection(Double angle) {
        if (angle == null) return null;

        if (angle >= 337.5 || angle < 22.5) return WindDirection.NORTH;
        else if (angle < 67.5) return WindDirection.NORTH_EAST;
        else if (angle < 112.5) return WindDirection.EAST;
        else if (angle < 157.5) return WindDirection.SOUTH_EAST;
        else if (angle < 202.5) return WindDirection.SOUTH;
        else if (angle < 247.5) return WindDirection.SOUTH_WEST;
        else if (angle < 292.5) return WindDirection.WEST;
        else return WindDirection.NORTH_WEST;
    }


    /**
     * 단일 예보 데이터 기반 우천 취소 확률 예측
     */
    public static double predictRainoutProbability(WeatherRequest.SaveDTO.WeatherDTO weather) {
        double score = 0.0;

        // 강수확률 비중: 최대 30점
        if (weather.getRainPer() != null) {
            score += weather.getRainPer() * 0.3;  // 예: 80% -> 24점
        }

        // 강수량 비중: 최대 50점 (5mm 이상이면 50점 고정)
        if (weather.getRainAmount() != null) {
            score += Math.min(weather.getRainAmount(), 5.0) * 10;  // 예: 2.5mm -> 25점
        }

        // 풍속: 10m/s 이상이면 10점
        if (weather.getWindSpeed() != null && weather.getWindSpeed() >= 10.0) {
            score += 10;
        }

        // 날씨코드가 비/눈인 경우 20점
        if (weather.getWeatherCode() == WFCD.DB04) {
            score += 20;
        }

        // 기온이 너무 낮은 경우 추가 점수 (예: 5도 이하)
        if (weather.getTemperature() != null && weather.getTemperature() <= 5.0) {
            score += 10;
        }

        return Math.min(score, 100.0);
    }

    /**
     * double 파싱에 실패할 경우 0.0 반환
     */
    public static double safeParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 초단기 + 단기 예보 조합 기반 우천 취소 확률 계산
     */
    public static double predictRainoutFromBoth(
            WeatherUltraRequest.SaveDTO.WeatherDTO dto, Double rainPer
    ) {
        double rainAmount = dto.getRainAmount() != null ? dto.getRainAmount() : 0.0;
        double humidity = dto.getHumidityPer() != null ? dto.getHumidityPer() : 0.0;
        double wind = dto.getWindSpeed() != null ? dto.getWindSpeed() : 0.0;
        double rainProb = rainPer != null ? rainPer : 0.0;

        double score = rainAmount * 1.5 + humidity * 0.3 + wind * 0.2 + rainProb * 0.4;
        return Math.min(100.0, Math.round(score * 10.0) / 10.0);
    }


    /**
     * 주어진 경기 시간 기준으로, 초단기예보에 사용할 가장 가까운 base_time (예보 기준 시각)을 구한다.
     * 초단기 예보는 1시간 뒤의 예보를 제공하기 때문에, 기준 시각은 1시간 전으로 보정한다.
     * 시간은 항상 30분 단위로 반올림된다 (예: 13:15 → 12:30, 13:40 → 13:30).
     */
    public static String getBaseTime(LocalDateTime gameTime) {
        // 초단기 예보는 1시간 뒤의 날씨를 제공하므로, 1시간 이전 시점 기준 baseTime 설정
        LocalDateTime target = gameTime.minusHours(1);

        int hour = target.getHour();
        int minute = target.getMinute();

        // 00분 ~ 29분이면 이전 시각의 30분
        // 30분 이후면 현재 시각의 30분
        if (minute < 30) {
            hour -= 1;
            minute = 30;
        } else {
            minute = 30;
        }

        // baseTime 포맷: "HHmm"
        LocalDateTime base = LocalDateTime.of(
                target.getYear(),
                target.getMonth(),
                target.getDayOfMonth(),
                hour < 0 ? 23 : hour,
                minute
        );

        return base.format(DateTimeFormatter.ofPattern("HHmm"));
    }


    /**
     * 초단기예보 항목에서 제공되는 fcstDate (yyyyMMdd) 및 fcstTime (HHmm) 문자열을 Java의 Timestamp 객체로 변환한다.
     */
    public static Timestamp getForecastTimestamp(String fcstDate, String fcstTime) {
        String formattedDate = fcstDate.substring(0, 4) + "-" + fcstDate.substring(4, 6) + "-" + fcstDate.substring(6, 8);
        String formattedTime = fcstTime.substring(0, 2) + ":00:00";
        return Timestamp.valueOf(formattedDate + " " + formattedTime);
    }

    /**
     * LocalDateTime 객체로부터 기상청 초단기예보 API에 필요한 base_date 값을 추출한다.
     * 형식은 "yyyyMMdd" (예: "20250625")
     */
    public static String getBaseDate(LocalDateTime dateTime) {
        return dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
