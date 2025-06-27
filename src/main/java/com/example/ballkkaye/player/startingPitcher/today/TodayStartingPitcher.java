package com.example.ballkkaye.player.startingPitcher.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.player.Player;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "today_starting_pitcher_tb")
@Entity
public class TodayStartingPitcher {
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
}