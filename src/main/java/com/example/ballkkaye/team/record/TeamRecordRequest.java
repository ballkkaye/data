package com.example.ballkkaye.team.record;

import lombok.Data;

public class TeamRecordRequest {

    @Data
    public static class Dto {
        private Integer teamId;
        private Integer teamRank;
        private Integer totalGame;
        private Integer winGame;
        private Integer loseGame;
        private Integer tieGame;
        private Double winRate;
        private Double gap;
        private String recentTenGames;
        private String streak;
        private Double OPS;
        private Integer R;
        private Double ERA;

        public Dto(Integer teamId, Integer teamRank, Integer totalGame, Integer winGame, Integer loseGame, Integer tieGame,
                   Double winRate, Double gap, String recentTenGames, String streak, Double OPS, Integer r, Double ERA) {
            this.teamId = teamId;
            this.teamRank = teamRank;
            this.totalGame = totalGame;
            this.winGame = winGame;
            this.loseGame = loseGame;
            this.tieGame = tieGame;
            this.winRate = winRate;
            this.gap = gap;
            this.recentTenGames = recentTenGames;
            this.streak = streak;
            this.OPS = OPS;
            this.R = r;
            this.ERA = ERA;
        }
    }
}

