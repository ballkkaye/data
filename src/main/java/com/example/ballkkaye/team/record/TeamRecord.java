package com.example.ballkkaye.team.record;

import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "team_record_tb")
@Entity
public class TeamRecord {
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
    private Integer tieGame;

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
}