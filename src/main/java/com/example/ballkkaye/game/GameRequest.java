package com.example.ballkkaye.game;

import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

public class GameRequest {

    @Data
    @ToString
    public static class SaveDTO {
        private String gameCode;
        private Integer stadiumId;
        private Integer homeTeamId;
        private Integer awayTeamId;
        private Timestamp gameTime;
        private GameStatus gameStatus;
        private Integer homeResultScore;
        private Integer awayResultScore;
        private BroadcastChannel broadcastChannel;

        private Double homePredictionScore;
        private Double awayPredictionScore;
        private Double totalPredictionScore;
        private Double homeWinPer;
        private Double awayWinPer;

        public SaveDTO() {
            this.homeWinPer = 50.0;
            this.awayWinPer = 50.0;
        }

        public SaveDTO(String gameCode, Integer stadiumId, Integer homeTeamId, Integer awayTeamId, Timestamp gameTime,
                       GameStatus gameStatus, Integer homeResultScore, Integer awayResultScore,
                       BroadcastChannel broadcastChannel,
                       Double homePredictionScore, Double awayPredictionScore, Double totalPredictionScore,
                       Double homeWinPer, Double awayWinPer) {
            this.gameCode = gameCode;
            this.stadiumId = stadiumId;
            this.homeTeamId = homeTeamId;
            this.awayTeamId = awayTeamId;
            this.gameTime = gameTime;
            this.gameStatus = gameStatus;
            this.homeResultScore = homeResultScore;
            this.awayResultScore = awayResultScore;
            this.broadcastChannel = broadcastChannel;
            this.homePredictionScore = homePredictionScore; // 연산 필요
            this.awayPredictionScore = awayPredictionScore; // 연산 필요
            this.totalPredictionScore = totalPredictionScore; // 연산 필요
            this.homeWinPer = homeWinPer != null ? homeWinPer : 50.0;
            this.awayWinPer = awayWinPer != null ? awayWinPer : 50.0;
        }

        public static SaveDTO fromGameData(GameService.GameData gameData) {
            SaveDTO dto = new SaveDTO();
            dto.setGameCode(gameData.getGameCode());
            dto.setStadiumId(gameData.getStadiumId());
            dto.setHomeTeamId(gameData.getHomeTeamId());
            dto.setAwayTeamId(gameData.getAwayTeamId());
            dto.setGameTime(gameData.getGameTime());
            dto.setGameStatus(gameData.getGameStatus());
            dto.setHomeResultScore(gameData.getHomeResultScore());
            dto.setAwayResultScore(gameData.getAwayResultScore());
            dto.setBroadcastChannel(gameData.getBroadcastChannel());

            dto.setHomePredictionScore(null);
            dto.setAwayPredictionScore(null);
            dto.setTotalPredictionScore(null);
            dto.setHomeWinPer(50.0);
            dto.setAwayWinPer(50.0);

            return dto;
        }
    }
}
