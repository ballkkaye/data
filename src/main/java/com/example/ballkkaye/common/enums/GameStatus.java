package com.example.ballkkaye.common.enums;

public enum GameStatus {
    SCHEDULED("경기예정"),
    IN_PROGRESS("경기중"),
    COMPLETED("경기종료"),
    CANCELLED("경기취소"),
    DELAYED("경기지연");

    private final String state;

    GameStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}