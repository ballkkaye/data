package com.example.ballkkaye.weather;

import com.example.ballkkaye._core.error.ex.Exception404;
import com.example.ballkkaye._core.util.Util;
import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.stadium.StadiumRepository;
import com.example.ballkkaye.stadium.stadiumCoordinate.StadiumCoordinate;
import com.example.ballkkaye.stadium.stadiumCoordinate.StadiumCoordinateRepository;
import com.example.ballkkaye.weather.weatherUltra.WeatherUltra;
import com.example.ballkkaye.weather.weatherUltra.WeatherUltraRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.ballkkaye._core.util.Util.safeParseDouble;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final StadiumCoordinateRepository stadiumCoordinateRepository;
    private final GameRepository gameRepository;
    private final WeatherUltraRepository weatherUltraRepository;
    private final StadiumRepository stadiumRepository;


    @Value("${WEATHER_API_AUTH_KEY}")
    private String weatherApiAuthKey;


    /**
     * [단기예보 수집 및 저장]
     * 기상청 단기예보 API를 호출하여 오늘 경기장의 날씨 데이터를 가져와 `weather` 테이블에 저장한다.
     * - 강수확률(POP), 강수량(PCP), 풍속, 풍향, 온도 등 항목을 파싱
     * - 경기 시간에 대한 예보 시간(`forecastAt`) 기준으로 저장됨
     * - API 기준 baseTime은 항상 오전 5시로 고정 (당일 예보용)
     */
    @Transactional
    public void getShortForecastAndSave() {
        String authKey = weatherApiAuthKey;  // 기상청 API 인증키

        // 오늘 날짜 기준으로 열리는 경기 조회
        List<Game> todayGames = gameRepository.findByToday();

        for (Game game : todayGames) {
            Integer gameId = game.getId();
            Timestamp gameTime = game.getGameTime();
            Stadium stadium = game.getStadium();
            Integer stadiumId = stadium.getId();


            // 해당 경기장의 위도/경도를 격자 좌표로 변환
            StadiumCoordinate coord = stadiumCoordinateRepository.findByStadiumId(stadiumId)
                    .orElseThrow(() -> new Exception404(("구장 위/경도 정보 없음")));
            Util.GridXY grid = Util.convertToGrid(coord.getLatitude(), coord.getLongitude());

            String baseDate = gameTime.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = "0500"; // 단기예보는 하루 4회 제공되며, 오전 5시 예보가 당일 기준으로 가장 최신

            try {
                // ① 기상청 단기예보 API 호출
                Connection.Response response = Jsoup.connect("https://apihub.kma.go.kr/api/typ02/openApi/VilageFcstInfoService_2.0/getVilageFcst")
                        .method(Connection.Method.GET)
                        .data("numOfRows", "1000")
                        .data("pageNo", "1")
                        .data("dataType", "JSON")
                        .data("base_date", baseDate)
                        .data("base_time", baseTime)
                        .data("nx", String.valueOf(grid.nx))
                        .data("ny", String.valueOf(grid.ny))
                        .data("authKey", authKey)
                        .ignoreContentType(true)
                        .execute();

                // ② 응답 JSON에서 item 배열 파싱
                JsonArray items = JsonParser.parseString(response.body())
                        .getAsJsonObject()
                        .getAsJsonObject("response")
                        .getAsJsonObject("body")
                        .getAsJsonObject("items")
                        .getAsJsonArray("item");

                // ③ 예보 시간별로 항목들을 묶어서 DTO 구성
                Map<Timestamp, WeatherRequest.SaveDTO.WeatherDTO> forecastMap = new TreeMap<>();

                // 기상 카테고리별 값 매핑
                for (JsonElement elem : items) {
                    JsonObject obj = elem.getAsJsonObject();
                    String category = obj.get("category").getAsString();  // 예: TMP, REH, PTY 등
                    String fcstDate = obj.get("fcstDate").getAsString();  // 예: "20250626"
                    String fcstTime = obj.get("fcstTime").getAsString();  // 예: "1500"
                    String fcstValue = obj.get("fcstValue").getAsString();  // 예: "24.5" or "강수없음"

                    if (!fcstDate.equals(baseDate)) continue;  // 오늘 예보만 처리

                    Timestamp forecastAt = Util.getForecastTimestamp(fcstDate, fcstTime);
                    WeatherRequest.SaveDTO.WeatherDTO dto = forecastMap.getOrDefault(forecastAt,
                            new WeatherRequest.SaveDTO.WeatherDTO(null, null, null, null, null, null, forecastAt, null));

                    // 카테고리 값 파싱 및 DTO 매핑
                    switch (category) {
                        case "TMP", "T1H" -> dto.setTemperature(safeParseDouble(fcstValue));
                        case "REH" -> dto.setHumidityPer(safeParseDouble(fcstValue));
                        case "WSD" -> dto.setWindSpeed(safeParseDouble(fcstValue));
                        case "VEC" -> dto.setWindDirection(Util.mapVecToDirection(safeParseDouble(fcstValue)));
                        case "PTY" -> dto.setWeatherCode(switch (fcstValue) {
                            case "0" -> WFCD.DB01;  // 맑음
                            case "1" -> WFCD.DB02;  // 비
                            case "2" -> WFCD.DB03;  // 비/눈
                            case "3" -> WFCD.DB04;  // 눈
                            case "4" -> WFCD.DB05;  // 소나기
                            case "5" -> WFCD.DB06;  // 빗방울
                            case "6" -> WFCD.DB07;  // 눈날림
                            case "7" -> WFCD.DB08;  // 빗방울/눈날림
                            default -> null;
                        });
                        case "POP" -> dto.setRainPer(safeParseDouble(fcstValue));  // 강수확률
                        case "PCP" -> {  // 강수량
                            if (fcstValue.equals("강수없음")) {
                                dto.setRainAmount(0.0);
                            } else if (fcstValue.contains("미만")) {
                                dto.setRainAmount(0.5);
                            } else {
                                dto.setRainAmount(safeParseDouble(fcstValue.replace("mm", "")));
                            }
                        }
                    }

                    forecastMap.put(forecastAt, dto);
                }

                // ④ Weather 엔티티로 변환하여 저장
                List<Weather> weatherList = new ArrayList<>();

                for (WeatherRequest.SaveDTO.WeatherDTO dto : forecastMap.values()) {
                    Game g = gameRepository.findById(gameId)
                            .orElseThrow(() -> new Exception404(("경기 정보 없음")));
                    Stadium s = stadiumRepository.findById(stadiumId)
                            .orElseThrow(() -> new Exception404(("구장 정보 없음")));

                    // 엔티티 매핑
                    Weather entity = Weather.builder()
                            .game(g)
                            .stadium(s)
                            .forecastAt(dto.getForecastAt())
                            .temperature(dto.getTemperature())
                            .humidityPer(dto.getHumidityPer())
                            .windSpeed(dto.getWindSpeed())
                            .windDirection(dto.getWindDirection())
                            .weatherCode(dto.getWeatherCode())
                            .rainPer(dto.getRainPer())
                            .rainAmount(dto.getRainAmount())
                            .build();

                    weatherList.add(entity);

                }

                // DB 저장
                weatherRepository.saveAll(weatherList);

            } catch (Exception e) {
                Sentry.captureException(e);
                log.error("날씨 API 호출 실패 - 경기 ID: " + gameId + ", 이유: " + e.getMessage());
            }
        }
    }


    /**
     * [단기예보 → 초단기 예보용 테이블 복사]
     * Weather 테이블의 데이터를 기반으로 WeatherUltra 테이블에 복사한다.
     * - 초단기예보 API 실행 전에 fallback 또는 예보 보완용으로 사용
     */
    @Transactional
    public void copyShortToUltra() {
        List<Game> gamesToday = gameRepository.findByToday();

        for (Game game : gamesToday) {
            Integer gameId = game.getId();

            // 해당 경기의 예보 데이터를 조회 (예보일 기준 필터링)
            List<Weather> weathers = weatherRepository.findByGameIdAndDate(
                    gameId, game.getGameTime().toLocalDateTime().toLocalDate()
            );

            List<WeatherUltra> ultraList = new ArrayList<>();

            for (Weather w : weathers) {
                Game wgame = gameRepository.findById(w.getGame().getId())
                        .orElseThrow(() -> new Exception404(("경기 정보 없음: " + w.getGame().getId())));
                Stadium stadium = stadiumRepository.findById(w.getStadium().getId())
                        .orElseThrow(() -> new Exception404(("구장 정보 없음: " + w.getStadium().getId())));
                WeatherUltra ultra = WeatherUltra.builder()
                        .game(wgame)
                        .stadium(stadium)
                        .forecastAt(w.getForecastAt())
                        .temperature(w.getTemperature())
                        .humidityPer(w.getHumidityPer())
                        .windSpeed(w.getWindSpeed())
                        .windDirection(w.getWindDirection())
                        .weatherCode(w.getWeatherCode())
                        .rainAmount(w.getRainAmount())
                        .rainPer(w.getRainPer())
                        .build();

                ultraList.add(ultra);
            }

            // WeatherUltra 테이블에 저장
            weatherUltraRepository.saveAll(ultraList);
        }
    }
}
