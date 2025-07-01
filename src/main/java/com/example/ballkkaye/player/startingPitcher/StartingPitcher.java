package com.example.ballkkaye.player.startingPitcher;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.player.Player;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "starting_pitcher_tb")
@Entity
public class StartingPitcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Player player;

    @Column
    private String profileUrl;

    @Column
    private Double ERA;

    @Column
    private Integer gameCount;

    @Column
    private String result;

    @Column
    private Integer QS;

    @Column
    private Double WHIP;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public StartingPitcher(Integer id, Game game, Player player, String profileUrl, Double ERA, Integer gameCount, String result, Integer QS, Double WHIP) {
        this.id = id;
        this.game = game;
        this.player = player;
        this.profileUrl = profileUrl;
        this.ERA = ERA;
        this.gameCount = gameCount;
        this.result = result;
        this.QS = QS;
        this.WHIP = WHIP;
    }
}