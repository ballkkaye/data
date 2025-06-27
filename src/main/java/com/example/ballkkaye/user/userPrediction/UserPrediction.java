package com.example.ballkkaye.user.userPrediction;

import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "user_prediction_tb")
@Entity
public class UserPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Game game;

    @Column
    private Integer userChoice;

    @Column(nullable = false)
    private String result;

    @CreationTimestamp
    private Timestamp createdAt;
}