package com.example.ballkkaye.user.userPrediction;

import com.example.ballkkaye.common.enums.PredictionStatus;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "user_prediction_tb")
@Entity
public class UserPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id")
    private Team userChoiceTeam;

    @Column
    @Enumerated(EnumType.STRING)
    private PredictionStatus result;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public void updateResult(PredictionStatus result) {
        this.result = result;
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

}