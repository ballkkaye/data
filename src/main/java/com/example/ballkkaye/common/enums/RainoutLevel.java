package com.example.ballkkaye.common.enums;

public enum RainoutLevel {

    HIGH("우천취소 가능성 높음", 70),
    MEDIUM("우천취소 가능성 보통", 40),
    LOW("우천취소 가능성 낮음", 0);

    private final String message;
    private final int threshold;

    RainoutLevel(String message, int threshold) {
        this.message = message;
        this.threshold = threshold;
    }

    public String getMessage() {
        return message;
    }

    public static RainoutLevel fromScore(double score) {
        if (score >= HIGH.threshold) return HIGH;
        if (score >= MEDIUM.threshold) return MEDIUM;
        return LOW;
    }
}