package com.example.ballkkaye.common.enums;

import java.util.Arrays;

public enum BroadcastChannel {
    SPO_T("SPO-T"),
    SPO_2T("SPO-2T"),
    MS_T("MS-T"),
    SS_T("SS-T"),
    KN_T("KN-T"),
    S_T("S-T"),
    TVING("TVING"),
    K_2T("K-2T"),
    M_T("M-T"),
    UNKNOWN("알 수 없음");

    private final String channelCode;

    BroadcastChannel(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public static BroadcastChannel fromString(String rawChannelCode) {
        return Arrays.stream(BroadcastChannel.values())
                .filter(channel -> channel.channelCode.equalsIgnoreCase(rawChannelCode.trim()))
                .findFirst()
                .orElse(UNKNOWN);
    }
}