package com.example.ballkkaye.common.enums;

public enum Result {
    WIN("승"),
    LOSE("패"),
    TIE("무");
    
    private final String value;

    Result(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
