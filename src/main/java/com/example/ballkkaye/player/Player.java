package com.example.ballkkaye.player;

import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "player_tb")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer kboPlayerId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Builder
    public Player(Integer id, Integer kboPlayerId, String name, Team team) {
        this.id = id;
        this.kboPlayerId = kboPlayerId;
        this.name = name;
        this.team = team;
    }
}