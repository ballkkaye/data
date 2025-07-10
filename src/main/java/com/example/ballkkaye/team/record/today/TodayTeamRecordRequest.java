package com.example.ballkkaye.team.record.today;

import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.team.record.TeamRecord;
import lombok.Data;

import java.sql.Timestamp;

public class TodayTeamRecordRequest {

    @Data
    public static class saveDto {
        private Integer teamId;
        private Integer teamRank;
        private Integer totalGame;
        private Integer winGame;
        private Integer loseGame;
        private Integer tieGame;
        private Double winRate;
        private Double gap;
        private String recentTenGame;
        private String streak;
        private Double ops;
        private Integer r;
        private Double era;
        private Timestamp createdAt;

        public saveDto(TeamRecord tr) {
            this.teamId = tr.getTeam().getId();
            this.teamRank = tr.getTeamRank();
            this.totalGame = tr.getTotalGame();
            this.winGame = tr.getWinGame();
            this.loseGame = tr.getLoseGame();
            this.tieGame = tr.getTieGame();
            this.winRate = tr.getWinRate();
            this.gap = tr.getGap();
            this.recentTenGame = tr.getRecentTenGame();
            this.streak = tr.getStreak();
            this.ops = tr.getOPS();
            this.r = tr.getR();
            this.era = tr.getERA();
            this.createdAt = tr.getCreatedAt();
        }

        public TodayTeamRecord toEntity(Team team) {
            return TodayTeamRecord.builder()
                    .team(team)
                    .teamRank(teamRank)
                    .totalGame(totalGame)
                    .winGame(winGame)
                    .loseGame(loseGame)
                    .tieGame(tieGame)
                    .winRate(winRate)
                    .gap(gap)
                    .recentTenGame(recentTenGame)
                    .streak(streak)
                    .OPS(ops)
                    .R(r)
                    .ERA(era)
                    .createdAt(createdAt)
                    .build();
        }
    }
}
