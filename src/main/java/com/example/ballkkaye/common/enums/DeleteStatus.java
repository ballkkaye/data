package com.example.ballkkaye.common.enums;

public enum DeleteStatus {
    NOT_DELETED("정상"),
    DELETED("삭제됨");

    private final String label;

    DeleteStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}