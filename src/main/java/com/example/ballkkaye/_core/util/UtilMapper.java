package com.example.ballkkaye._core.util;

import java.util.Map;

public class UtilMapper {

    private static final Map<String, Integer> teamNameToId = Map.of(
            "LG", 1, "두산", 2, "키움", 3, "SSG", 4,
            "KIA", 5, "삼성", 6, "롯데", 7, "한화", 8, "NC", 9, "KT", 10
    );

    public static Integer getTeamId(String shortName) {
        return teamNameToId.get(shortName);
    }
}
