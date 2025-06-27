package com.example.ballkkaye.common.enums;

public enum StadiumType {
    INDOOR("실내"),
    OUTDOOR("실외");

    private final String name;

    StadiumType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
