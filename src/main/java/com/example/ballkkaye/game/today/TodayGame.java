package com.example.ballkkaye.game.today;

import com.example.ballkkaye.common.enums.BroadcastChannel;
import com.example.ballkkaye.common.enums.GameStatus;
import com.example.ballkkaye.stadium.Stadium;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "today_game_tb")
@Entity
public class TodayGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team awayTeam;

    @Column(nullable = false)
    private Timestamp gameTime;

    @Column
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column
    private Integer homeResultScore;

    @Column
    private Integer awayResultScore;

    @Column(nullable = false)
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
    public TodayGame(Stadium stadium,
                     Team homeTeam,
                     Team awayTeam,
                     Timestamp gameTime,
                     GameStatus gameStatus,
                     Integer homeResultScore,
                     Integer awayResultScore,
                     BroadcastChannel broadcastChannel,
                     Double homePredictionScore,
                     Double awayPredictionScore,
                     Double totalPredictionScore,
                     Double homeWinPer,
                     Double awayWinPer) {
        this.stadium = stadium;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameTime = gameTime;
        this.gameStatus = gameStatus;
        this.homeResultScore = homeResultScore;
        this.awayResultScore = awayResultScore;
        this.broadcastChannel = broadcastChannel;
        this.homePredictionScore = homePredictionScore;
        this.awayPredictionScore = awayPredictionScore;
        this.totalPredictionScore = totalPredictionScore;
        this.homeWinPer = homeWinPer;
        this.awayWinPer = awayWinPer;
    }
}