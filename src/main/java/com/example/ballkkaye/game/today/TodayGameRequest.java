package com.example.ballkkaye.game.today;

import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.team.Team;
import lombok.Data;

import java.sql.Timestamp;

public class TodayGameRequest {

    @Data
    public static class SaveRequest {
        private Stadium stadium;
        private Team homeTeam;
        private Team awayTeam;
        private Timestamp gameTime;
        private GameStatus gameStatus;
        private Integer homeResultScore;
        private Integer awayResultScore;
        private BroadcastChannel broadcastChannel;
        private Double homeWinPer;
        private Double awayWinPer;

        public TodayGame toEntity() {
            Double total = (homeWinPer != null && awayWinPer != null)
                    ? homeWinPer + awayWinPer : null;

            return TodayGame.builder()
                    .stadium(stadium)
                    .homeTeam(homeTeam)
                    .awayTeam(awayTeam)
                    .gameTime(gameTime)
                    .gameStatus(gameStatus)
                    .homeResultScore(homeResultScore)
                    .awayResultScore(awayResultScore)
                    .broadcastChannel(broadcastChannel)
                    .homePredictionScore(null)
                    .awayPredictionScore(null)
                    .totalPredictionScore(total)
                    .homeWinPer(homeWinPer)
                    .awayWinPer(awayWinPer)
                    .build();
        }
    }
}
