package com.example.ballkkaye._core.util;

import com.example.ballkkaye.common.enums.WindDirection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Util {

    /**
     * 맞대결 전적 페이지 셀렉트 박스의 옵션 중 keyword를 포함한 항목을 선택
     * 포함된 항목이 없으면 예외 반환
     */
    public static void selectByContainingText(Select select, String keyword) {
        boolean found = false;
        for (WebElement option : select.getOptions()) {
            if (option.getText().contains(keyword)) {
                select.selectByVisibleText(option.getText());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException("드롭다운에서 '" + keyword + "' 포함된 옵션 없음");
        }
    }


    /**
     * 타자의 시즌 타율 문자열을 Double로 안전하게 파싱.
     * 마지막 컬럼에서 값을 가져오며, "-" 또는 공백이면 0.0을 반환
     */
    public static Double parseSeasonAVG(List<WebElement> cols) {
        try {
            String avgText = cols.get(cols.size() - 1).getText().trim(); // 예: ".294", "-" 등
            if (avgText.equals("-") || avgText.isEmpty()) return null; // 정보 없음은 null
            return Double.parseDouble(avgText);
        } catch (Exception e) {
            return null; // 파싱 실패도 null 처리
        }
    }


    /**
     * 문자열을 Integer로 변환하되, 실패 시 0을 반환
     */
    public static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

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

    /**
     * 문자열을 double로 변환하되, 실패 시 0.0을 반환
     */
    public static double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    /**
     * 전체 구장 이름을 KBO 메인페이지의 "game-cont" 엘리먼트의 `s_nm` 값에 맞춰 단축 형태로 변환함.
     * 예: "잠실야구장" → "잠실"
     * KBO 메인페이지의 경기 정보(li.game-cont)의 stadium 이름은 축약형으로 제공되기 때문에,
     * DB 또는 API상의 전체 구장명을 매핑해주는 용도임.
     */
    public static String simplifyStadiumName(String fullName) {
        return switch (fullName.trim()) {
            case "잠실야구장" -> "잠실";
            case "고척스카이돔" -> "고척";
            case "수원 KT위즈파크" -> "수원";
            case "인천 SSG 랜더스필드" -> "문학";
            case "광주-기아 챔피언스필드" -> "광주";
            case "대구 삼성라이온즈파크" -> "대구";
            case "부산 사직야구장" -> "사직";
            case "대전 한화생명이글스파크" -> "대전(신)";
            case "창원 NC파크" -> "창원";
            case "청주 야구장" -> "청주";
            case "울산 문수야구장" -> "울산";
            case "포항 야구장" -> "포항";
            case "군산 월명야구장" -> "군산";
            default -> fullName; // fallback
        };
    }


    /**
     * HTML 문서에서 시즌 전적 텍스트를 추출하는 유틸 메서드.
     * 예: "시즌 10승 3패 VS 상대 ..." → "10승 3패"
     */
    public static String parseResultString(Document doc) {
        Element recordEl = doc.selectFirst(".record");
        if (recordEl == null) return "없음";
        String txt = recordEl.text().replace("시즌 ", "").trim();
        return txt.isEmpty() ? "없음" : txt.contains("VS") ? txt.split("VS")[0].trim() : txt;
    }


    /**
     * HTML 문서 내 두 번째 <img> 태그에서 프로필 이미지 URL 추출.
     * URL이 //로 시작하면 https: 접두사를 붙여 반환.
     */
    public static String parseImgUrl(Document doc) {
        Elements imgs = doc.select("img");
        if (imgs.size() >= 2) {
            String path = imgs.get(1).attr("src");
            return path.startsWith("http") ? path : "https:" + path;
        }
        return "";
    }

    /**
     * 문자열을 안전하게 Double로 파싱하는 유틸 메서드.
     * null, 빈 문자열, "-" 등은 null로 처리.
     */
    public static Double parseNullableDouble(String s) {
        if (s == null || s.trim().equals("-")) return null;
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }


    /**
     * 문자열을 안전하게 Integer로 파싱하는 유틸 메서드.
     * null, 빈 문자열, "-" 등은 null로 처리.
     */
    public static Integer safeParseInt(String s) {
        if (s == null || s.trim().equals("-")) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
