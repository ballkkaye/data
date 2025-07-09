package com.example.ballkkaye.common.enums;

public enum PredictionStatus {
    CORRECT("정답"),
    INCORRECT("오답"),
    TIE("무승부"),
    WAITING("대기 중"),
    CLOSED("예측 마감");

    private final String value;

    PredictionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}