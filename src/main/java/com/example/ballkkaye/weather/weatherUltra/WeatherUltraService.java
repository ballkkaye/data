package com.example.ballkkaye.weather.weatherUltra;

import com.example.ballkkaye._core.util.Util;
import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.game.GameRepository;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.stadium.stadiumCoordinate.StadiumCoordinate;
import com.example.ballkkaye.stadium.stadiumCoordinate.StadiumCoordinateRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.ballkkaye._core.util.Util.safeParseDouble;

@RequiredArgsConstructor
@Service
public class WeatherUltraService {
    private final WeatherUltraRepository weatherUltraRepository;
    private final StadiumCoordinateRepository stadiumCoordinateRepository;
    private final GameRepository gameRepository;


    /**
     * [스케줄러 진입점]
     * - 오늘 경기 리스트를 조회하여, 각 경기의 초단기예보를 수집/저장
     * - 단, 현재 시간이 경기 시작 1시간 전 ±5분 이내인 경기만 처리
     */
    @Transactional
    public void getUltraForecastAndSave() {
        String authKey = "zFoyjf6aR62aMo3-muetCg"; // 기상청 API 인증키
        List<Game> todayGames = gameRepository.findByToday();
        LocalDateTime now = LocalDateTime.now();

        for (Game game : todayGames) {
            LocalDateTime gameDateTime = game.getGameTime().toLocalDateTime();

            // 경기 시작 1시간 전 ±5분 이내에 해당될 경우만 처리
            if (!now.isBefore(gameDateTime.minusHours(1).minusMinutes(5)) &&
                    !now.isAfter(gameDateTime.minusHours(1).plusMinutes(5))) {
                processUltraForecastForGame(game, authKey);
                System.out.println("🎯 예보 처리됨: gameId=" + game.getId() + ", now=" + now); // 확인용
            }

        }
    }


    /**
     * [단일 경기 초단기예보 수집/저장]
     * - Jsoup + 기상청 API로 초단기 예보를 요청
     * - 카테고리별로 데이터를 파싱하여 `WeatherUltra` 엔티티 생성
     * - 동일 예보시간이 있을 경우 업데이트, 없으면 새로 저장
     */
    public void processUltraForecastForGame(Game game, String authKey) {
        // [1] 경기 기본 정보 추출
        Integer gameId = game.getId();
        Timestamp gameTime = game.getGameTime();
        Stadium stadium = game.getStadium();
        Integer stadiumId = stadium.getId();

        // [2] 해당 구장의 위도/경도를 격자 좌표로 변환 (API 요청에 필요)
        StadiumCoordinate coord = stadiumCoordinateRepository.findByStadiumId(stadiumId);
        Util.GridXY grid = Util.convertToGrid(coord.getLatitude(), coord.getLongitude());

        // [3] API 요청용 날짜/시간 파라미터 계산
        LocalDateTime gameDateTime = gameTime.toLocalDateTime();
        String baseDate = Util.getBaseDate(gameDateTime);  // yyyyMMdd 형식
        String baseTime = Util.getBaseTime(gameDateTime);  // 1시간 단위 base_time (예: 1700)

        try {
            // [4] 기상청 초단기예보 API 호출 (JSON 형식 요청)
            Connection.Response response = Jsoup.connect("https://apihub.kma.go.kr/api/typ02/openApi/VilageFcstInfoService_2.0/getUltraSrtFcst")
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

            // [5] 응답 JSON 파싱: item 배열 추출
            JsonArray items = JsonParser.parseString(response.body())
                    .getAsJsonObject()
                    .getAsJsonObject("response")
                    .getAsJsonObject("body")
                    .getAsJsonObject("items")
                    .getAsJsonArray("item");

            // [6] forecastAt(예보시각)을 기준으로 날씨 데이터를 그룹핑
            Map<Timestamp, WeatherUltraRequest.SaveDTO.WeatherDTO> forecastMap = new TreeMap<>();

            // 카테고리별 데이터 파싱
            for (JsonElement elem : items) {
                JsonObject obj = elem.getAsJsonObject();
                String category = obj.get("category").getAsString(); // 예: T1H, PTY, etc.
                String fcstDate = obj.get("fcstDate").getAsString();
                String fcstTime = obj.get("fcstTime").getAsString();
                String fcstValue = obj.get("fcstValue").getAsString();

                if (!fcstDate.equals(baseDate)) continue;

                // 예보 시각 계산
                Timestamp forecastAt = Util.getForecastTimestamp(fcstDate, fcstTime);
                WeatherUltraRequest.SaveDTO.WeatherDTO dto = forecastMap.getOrDefault(forecastAt,
                        new WeatherUltraRequest.SaveDTO.WeatherDTO(null, null, null, null, null, forecastAt, null));

                // 카테고리별로 DTO 필드 설정
                switch (category) {
                    case "T1H", "TMP" -> dto.setTemperature(safeParseDouble(fcstValue));
                    case "REH" -> dto.setHumidityPer(safeParseDouble(fcstValue));
                    case "WSD" -> dto.setWindSpeed(safeParseDouble(fcstValue));
                    case "VEC" -> dto.setWindDirection(Util.mapVecToDirection(safeParseDouble(fcstValue)));
                    case "PTY" -> dto.setWeatherCode(switch (fcstValue) {
                        case "0" -> WFCD.DB01;
                        case "1" -> WFCD.DB02;
                        case "2" -> WFCD.DB03;
                        case "3" -> WFCD.DB04;
                        case "4" -> WFCD.DB05;
                        case "5" -> WFCD.DB06;
                        case "6" -> WFCD.DB07;
                        case "7" -> WFCD.DB08;
                        default -> null;
                    });
                    case "RN1" -> {
                        // RN1은 단위가 mm 또는 문자열(강수없음)일 수 있음
                        if (fcstValue.contains("강수없음")) {
                            dto.setRainAmount(0.0);
                        } else if (fcstValue.contains("미만")) {
                            dto.setRainAmount(0.5); // 미만의 경우 평균값 보정
                        } else {
                            dto.setRainAmount(safeParseDouble(fcstValue.replace("mm", "")));
                        }
                    }
                }

                forecastMap.put(forecastAt, dto);
            }

            // [7] 각 예보 시각에 대해 rainPer 보정값을 참조하여 DTO → Entity로 변환
            List<WeatherUltra> toSaveList = new ArrayList<>();

            for (WeatherUltraRequest.SaveDTO.WeatherDTO dto : forecastMap.values()) {
                Timestamp forecastAt = dto.getForecastAt();

                // 기존 단기예보에서 rainPer 값을 찾아서 가져옴 (같은 stadium + 시간 범위 내)
                List<WeatherUltra> priorShort = weatherUltraRepository.findByStadiumAndForecastDateRange(
                        stadium,
                        gameTime.toLocalDateTime().toLocalDate(),
                        Timestamp.valueOf(forecastAt.toLocalDateTime().minusHours(1)),
                        Timestamp.valueOf(forecastAt.toLocalDateTime().plusHours(1))
                );

                Double rainPer = priorShort.stream()
                        .map(WeatherUltra::getRainPer)
                        .filter(v -> v != null)
                        .findFirst()
                        .orElse(null);

                // [8] DTO → Entity 변환
                WeatherUltra entity = WeatherUltra.builder()
                        .game(game)
                        .stadium(stadium)
                        .forecastAt(forecastAt)
                        .temperature(dto.getTemperature())
                        .humidityPer(dto.getHumidityPer())
                        .windSpeed(dto.getWindSpeed())
                        .windDirection(dto.getWindDirection())
                        .weatherCode(dto.getWeatherCode())
                        .rainAmount(dto.getRainAmount())
                        .rainPer(rainPer)
                        .build();

                toSaveList.add(entity);

            }

            // [10] 저장 또는 업데이트 처리
            updateOrInsertAll(toSaveList);

        } catch (IOException e) {
            System.out.println("[ERROR] 초단기예보 API 실패 - 경기 ID: " + gameId + " / " + e.getMessage());
        }
    }

    /**
     * [DB 저장 로직]
     * - gameId, stadiumId, forecastAt 기준으로 기존 데이터 존재 여부 확인
     * - 있으면 update(), 없으면 새로 저장
     */
    @Transactional
    public void updateOrInsertAll(List<WeatherUltra> newList) {
        for (WeatherUltra incoming : newList) {
            // [1] 기존 동일 예보 시간의 데이터가 존재하는지 확인
            WeatherUltra existing = weatherUltraRepository
                    .findByGameAndStadiumAndForecastAtNearTime(
                            incoming.getGame(),
                            incoming.getStadium(),
                            incoming.getForecastAt()
                    );

            if (existing != null) {
                // [2] 존재하면 해당 엔티티 값만 갱신 (dirty checking)
                existing.update(
                        incoming.getTemperature(),
                        incoming.getHumidityPer(),
                        incoming.getWindSpeed(),
                        incoming.getWindDirection(),
                        incoming.getWeatherCode(),
                        incoming.getRainAmount(),
                        incoming.getRainPer()
                );
            } else {
                // [3] 존재하지 않으면 신규 엔티티로 저장
                weatherUltraRepository.save(incoming);
            }
        }
    }
}



