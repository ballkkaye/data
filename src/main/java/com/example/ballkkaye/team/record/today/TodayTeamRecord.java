package com.example.ballkkaye.team.record.today;

import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "today_team_record_tb")
@Entity
public class TodayTeamRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team team;

    @Column(nullable = false)
    private Integer teamRank;

    @Column(nullable = false)
    private Integer totalGame;

    @Column(nullable = false)
    private Integer winGame;

    @Column(nullable = false)
    private Integer loseGame;

    @Column(nullable = false)
    private Integer drawGame;

    @Column(nullable = false)
    private Double winRate;

    @Column(nullable = false)
    private Double gap;

    @Column(nullable = false)
    private String recentTenGame;

    @Column(nullable = false)
    private String streak;

    @Column(nullable = false)
    private Double OPS;

    @Column(nullable = false)
    private Integer R;

    @Column(nullable = false)
    private Double ERA;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public TodayTeamRecord(
            Integer id,
            Team team,
            Integer teamRank,
            Integer totalGame,
            Integer winGame,
            Integer loseGame,
            Integer drawGame,
            Double winRate,
            Double gap,
            String recentTenGame,
            String streak,
            Double OPS,
            Integer R,
            Double ERA,
            Timestamp createdAt

    ) {
        this.id = id;
        this.team = team;
        this.teamRank = teamRank;
        this.totalGame = totalGame;
        this.winGame = winGame;
        this.loseGame = loseGame;
        this.drawGame = drawGame;
        this.winRate = winRate;
        this.gap = gap;
        this.recentTenGame = recentTenGame;
        this.streak = streak;
        this.OPS = OPS;
        this.R = R;
        this.ERA = ERA;
        this.createdAt = createdAt;
    }
}