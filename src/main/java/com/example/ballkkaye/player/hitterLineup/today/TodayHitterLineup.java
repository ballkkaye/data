package com.example.ballkkaye.player.hitterLineup.today;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.player.Player;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "today_hitter_lineup_tb")
@Entity
public class TodayHitterLineup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Player player;

    @Column(nullable = false)
    private Integer todayHitterOrder;

    @Column(nullable = false)
    private String position;

    @Column
    private Integer ab;

    @Column
    private Integer h;

    @Column
    private Double avg;

    @Column
    private Double seasonAvg;

    @Column
    private Double ops;

    @CreationTimestamp
    private Timestamp createdAt;
}