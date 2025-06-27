package com.example.ballkkaye.common.enums;

public enum WindDirection {
    EAST("동풍"),
    WEST("서풍"),
    SOUTH("남풍"),
    NORTH("북풍"),
    NORTH_EAST("북동풍"),
    NORTH_WEST("북서풍"),
    SOUTH_EAST("남동풍"),
    SOUTH_WEST("남서풍");

    private final String name;

    WindDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
