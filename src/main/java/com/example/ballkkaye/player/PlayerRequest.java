package com.example.ballkkaye.player;

import lombok.Data;

public class PlayerRequest {

    @Data
    public static class Dto {
        private Integer kboPlayerId;
        private String name;
        private Integer teamId;

        public Dto(Integer kboPlayerId, String name, Integer teamId) {
            this.kboPlayerId = kboPlayerId;
            this.name = name;
            this.teamId = teamId;
        }
    }
}
