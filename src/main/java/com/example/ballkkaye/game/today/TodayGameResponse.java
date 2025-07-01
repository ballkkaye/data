package com.example.ballkkaye.game.today;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodayGameResponse {

    @Data
    public static class ListDTO {
        private final Integer gameId;
        private final String gameDate;
        private final String gameStatus;
        private final String stadiumName;
        private final String gameTime;
        private final String broadcastChannel;
        private final String homeStartingPitcher;
        private final String awayStartingPitcher;
        private final String ticketLink;

        public ListDTO(Integer gameId, LocalDateTime gameTime, String gameStatus,
                       String stadiumName, String broadcastChannel,
                       String homeStartingPitcher, String awayStartingPitcher,
                       String ticketLink) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            this.gameId = gameId;
            this.gameDate = gameTime.format(dateFormatter);
            this.gameStatus = gameStatus;
            this.stadiumName = stadiumName;
            this.gameTime = gameTime.format(timeFormatter);
            this.broadcastChannel = broadcastChannel;
            this.homeStartingPitcher = homeStartingPitcher != null ? homeStartingPitcher : "미정";
            this.awayStartingPitcher = awayStartingPitcher != null ? awayStartingPitcher : "미정";
            this.ticketLink = ticketLink;
        }
    }

}
