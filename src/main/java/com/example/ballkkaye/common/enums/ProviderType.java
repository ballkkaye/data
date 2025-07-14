package com.example.ballkkaye.common.enums;

public enum ProviderType {
    KAKAO("카카오"),
    GOOGLE("구글"),
    NAVER("네이버"),
    BALLKKAYE("볼까예");

    private final String label;

    ProviderType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

