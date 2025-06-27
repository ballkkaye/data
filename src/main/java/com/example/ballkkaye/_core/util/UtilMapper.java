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
}
