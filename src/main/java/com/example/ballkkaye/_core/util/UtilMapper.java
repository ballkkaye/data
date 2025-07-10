package com.example.ballkkaye._core.util;

import java.util.HashMap;
import java.util.Map;

public class UtilMapper {

    private static final Map<String, Integer> teamNameToId = Map.of(
            "LG", 1, "두산", 2, "키움", 3, "SSG", 4,
            "KIA", 5, "삼성", 6, "롯데", 7, "한화", 8, "NC", 9, "KT", 10
    );

    public static Integer getTeamId(String shortName) {
        return teamNameToId.get(shortName);
    }

    private static final Map<String, Integer> stadiumNameToId;

    static {
        stadiumNameToId = new HashMap<>();
        stadiumNameToId.put("잠실", 1);
        stadiumNameToId.put("고척", 2);
        stadiumNameToId.put("문학", 3);
        stadiumNameToId.put("광주", 4);
        stadiumNameToId.put("대구", 5);
        stadiumNameToId.put("사직", 6);
        stadiumNameToId.put("대전(신)", 7);
        stadiumNameToId.put("창원", 8);
        stadiumNameToId.put("수원", 9);
        stadiumNameToId.put("청주", 10);
        stadiumNameToId.put("울산", 11);
        stadiumNameToId.put("포항", 12);
        stadiumNameToId.put("군산", 13);
    }

    public static Integer getStadiumId(String shortName) {
        return stadiumNameToId.get(shortName);
    }


    /**
     * KBO teamCode와 매핑
     */
    private static final Map<String, Integer> teamCodeToId = Map.of(
            "LG", 1,   // LG 트윈스
            "OB", 2,   // 두산 베어스
            "WO", 3,   // 키움 히어로즈
            "SK", 4,   // SSG 랜더스
            "HT", 5,   // KIA 타이거즈
            "SS", 6,   // 삼성 라이온즈
            "LT", 7,   // 롯데 자이언츠
            "HH", 8,   // 한화 이글스
            "NC", 9,   // NC 다이노스
            "KT", 10   // KT 위즈
    );


    public static Integer getTeamIdByCode(String code) {
        return teamCodeToId.get(code);
    }


    /**
     * 팀 코드와 전체 팀명을 매핑하는 상수 Map.
     * - KBO 메인페이지에서 제공하는 팀 약어 코드(예: "OB", "LT")를
     * 실제 팀 전체 이름(예: "두산", "롯데")으로 매핑
     * 예: "OB" → "두산", "LT" → "롯데"
     */
    private static final Map<String, String> teamCodeToFullName = Map.of(
            "LG", "LG", "OB", "두산", "WO", "키움", "SK", "SSG",
            "HT", "KIA", "SS", "삼성", "LT", "롯데", "HH", "한화", "NC", "NC", "KT", "KT"
    );

    public static String getTeamFullNameByCode(String code) {
        return teamCodeToFullName.get(code); // "OB" → "두산"
    }

}
