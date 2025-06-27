package com.example.ballkkaye.common.enums;

public enum Region {
    SEOUL("서울"),
    GYEONGGI("경기"),
    INCHEON("인천"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    DAEJEON("대전"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    DAEGU("대구"),
    ULSAN("울산"),
    BUSAN("부산"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GWANGJU("광주"),
    JEJU("제주"),
    NONE("지역 무관");

    private final String name;

    Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
