package com.example.ballkkaye.game.today;

import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.game.Game;
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
        private Double homePredictionScore;
        private Double awayPredictionScore;

        private Game game;

        public TodayGame toEntity() {
            Double total = (homePredictionScore != null && awayPredictionScore != null)
                    ? homePredictionScore + awayPredictionScore : null;
            return TodayGame.builder()
                    .stadium(stadium)
                    .homeTeam(homeTeam)
                    .awayTeam(awayTeam)
                    .gameTime(gameTime)
                    .gameStatus(gameStatus)
                    .homeResultScore(homeResultScore)
                    .awayResultScore(awayResultScore)
                    .broadcastChannel(broadcastChannel)
                    .homePredictionScore(homePredictionScore)
                    .awayPredictionScore(awayPredictionScore)
                    .totalPredictionScore(total)
                    .homeWinPer(homeWinPer)
                    .awayWinPer(awayWinPer)
                    .game(game)
                    .build();
        }
    }

}
