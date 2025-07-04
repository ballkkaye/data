package com.example.ballkkaye.game;

import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "game_tb")
@Entity
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String gameCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team homeTeam;

    @Column(name = "game_status")
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column(nullable = false)
    private Timestamp gameTime;

    @Column
    private Integer homeResultScore;

    @Column
    private Integer awayResultScore;

    @Column
    @Enumerated(EnumType.STRING)
    private BroadcastChannel broadcastChannel;

    @Column
    private Double homePredictionScore;

    @Column
    private Double awayPredictionScore;

    @Column
    private Double totalPredictionScore;

    @Column(nullable = false)
    private Double homeWinPer;

    @Column(nullable = false)
    private Double awayWinPer;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Game(String gameCode, Stadium stadium, Team awayTeam, Team homeTeam, GameStatus gameStatus, Timestamp gameTime, Integer homeResultScore, Integer awayResultScore, BroadcastChannel broadcastChannel, Double homePredictionScore, Double awayPredictionScore, Double totalPredictionScore, Double homeWinPer, Double awayWinPer) {
        this.gameCode = gameCode;
        this.stadium = stadium;
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
        this.gameStatus = gameStatus;
        this.gameTime = gameTime;
        this.homeResultScore = homeResultScore;
        this.awayResultScore = awayResultScore;
        this.broadcastChannel = broadcastChannel;
        this.homePredictionScore = homePredictionScore;
        this.awayPredictionScore = awayPredictionScore;
        this.totalPredictionScore = totalPredictionScore;
        this.homeWinPer = homeWinPer;
        this.awayWinPer = awayWinPer;
    }

    public void update(GameStatus gameStatus,
                       Integer homeResultScore,
                       Integer awayResultScore) {
        this.gameStatus = gameStatus;
        this.homeResultScore = homeResultScore;
        this.awayResultScore = awayResultScore;
    }

}