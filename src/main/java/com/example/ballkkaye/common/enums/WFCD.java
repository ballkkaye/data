package com.example.ballkkaye.common.enums;

public enum WFCD {
    DB01("맑음"),     // PTY=0: 강수 없음
    DB02("비"),       // PTY=1: 비
    DB03("비/눈"),    // PTY=2: 비 또는 눈
    DB04("눈"),       // PTY=3: 눈
    DB05("소나기"),   // PTY=4: 소나기
    DB06("빗방울"),   // PTY=5: 빗방울
    DB07("눈날림"),   // PTY=6: 눈날림
    DB08("빗방울/눈날림"); // PTY=7: 빗방울/눈날림

    private final String code;

    WFCD(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
