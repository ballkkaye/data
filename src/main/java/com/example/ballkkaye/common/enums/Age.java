package com.example.ballkkaye.common.enums;

public enum Age {
    UNDER_20("~20대"),
    FROM_20_TO_30("20~30대"),
    FROM_30_TO_40("30~40대"),
    OVER_40("40대 이상"),
    NONE("연령 무관");

    private final String name;

    Age(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
