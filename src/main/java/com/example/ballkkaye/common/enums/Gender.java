package com.example.ballkkaye.common.enums;

public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    NONE("무관");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
