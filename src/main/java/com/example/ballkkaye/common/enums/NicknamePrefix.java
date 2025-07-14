package com.example.ballkkaye.common.enums;

public enum NicknamePrefix {
    dol("돌거북"),
    kal("칼날부리"),
    sim("심술두꺼비"),
    ba("바위게"),
    u("어스름늑대");

    private final String value;

    NicknamePrefix(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
