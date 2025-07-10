package com.example.ballkkaye.player.hitterLineup;

import lombok.Data;

import java.util.List;

public class HitterLineupRequest {

    @Data
    public static class HitterSaveDTO {

        private Integer gameId;
        private List<HitterInfo> hitters;


        @Data
        public static class HitterInfo {
            private Integer teamId;
            private Integer hitterOrder; // 타순
            private String name; // 선수명
            private String position; // 포지션 (내야수, 외야수, ...)
            private List<MachUpStatusDTO> machUpStatuses;  // 맞대결 전적 스탯 리스트
            private Double seasonAvg; // 타자의 시즌 타율

            // 임시 필드: 누구와 맞붙었는지
            private String vsPitcherName;
            private String vsPitcherTeam;

            @Data
            public static class MachUpStatusDTO {
                private Integer ab; // 선발투수와 맞대결 전적: 타수
                private Integer h; // 선발투수와 맞대결 전적: 안타수
                private Double avg; // 선발투수와 맞대결 전적: 타울
                private Double ops; // 선발투수와 맞대결 전적: 출루율 + 장타율

                public MachUpStatusDTO(Integer ab, Integer h, Double avg, Double ops) {
                    this.ab = ab;
                    this.h = h;
                    this.avg = avg;
                    this.ops = ops;
                }
            }


            public HitterInfo(Integer teamId, Integer hitterOrder, String name, String position, List<MachUpStatusDTO> machUpStatuses, Double seasonAvg, String vsPitcherName, String vsPitcherTeam) {
                this.teamId = teamId;
                this.hitterOrder = hitterOrder;
                this.name = name;
                this.position = position;
                this.machUpStatuses = machUpStatuses;
                this.seasonAvg = seasonAvg;
                this.vsPitcherName = vsPitcherName;
                this.vsPitcherTeam = vsPitcherTeam;
            }
        }


        public HitterSaveDTO(Integer gameId, List<HitterInfo> hitters) {
            this.gameId = gameId;
            this.hitters = hitters;
        }
    }
}
