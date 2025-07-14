package com.example.ballkkaye.user;

import com.example.ballkkaye.common.enums.Gender;
import com.example.ballkkaye.common.enums.PredictionTier;
import com.example.ballkkaye.common.enums.ProviderType;
import com.example.ballkkaye.common.enums.UserRole;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username; //

    @Column(nullable = false)
    private String password; //

    @Column(nullable = false)
    private String name; //

    @Column(nullable = false, unique = true)
    private String nickname; // << 유저가 입력

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team; // << 유저가 입력

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email; //

    @Column(nullable = false)
    private LocalDate birthDate; //

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; //

    @Column(nullable = false)
    private String profileUrl; //

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType; //

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole; //

    @Column
    private String fcmToken;

    @Column(nullable = false)
    private Integer predictionScore;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PredictionTier predictionTier;

    @CreationTimestamp
    private Timestamp createdAt; //

    @Builder
    public User(Integer id, String username, String password, String name, String nickname, Team team,
                String phoneNumber, String email, LocalDate birthDate, Gender gender,
                String profileUrl, ProviderType providerType, UserRole userRole, String fcmToken, Integer predictionScore, PredictionTier predictionTier, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.team = team;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.profileUrl = profileUrl;
        this.providerType = providerType;
        this.userRole = userRole;
        this.fcmToken = fcmToken;
        this.predictionScore = predictionScore;
        this.predictionTier = predictionTier;
        this.createdAt = createdAt;
    }

    public void additionalUserInfo(Team team, String nickname) {
        this.nickname = nickname == null ? this.nickname : nickname;
        this.team = team == null ? this.team : team;
    }

    public void updateUserInfo(Team team, String nickname, String profileUrl) {
        this.nickname = nickname == null ? this.nickname : nickname;
        this.team = team == null ? this.team : team;
        this.profileUrl = profileUrl == null ? this.profileUrl : profileUrl;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void updatePredictionScore(Integer predictionScore) {
        this.predictionScore = predictionScore == null ? this.predictionScore : predictionScore;
    }

    public void updatePredictionTier() {
        if (this.predictionScore >= 1000) {
            this.predictionTier = PredictionTier.DIAMOND;
        } else if (this.predictionScore >= 750) {
            this.predictionTier = PredictionTier.PLATINUM;
        } else if (this.predictionScore >= 500) {
            this.predictionTier = PredictionTier.GOLD;
        } else if (this.predictionScore >= 250) {
            this.predictionTier = PredictionTier.SILVER;
        } else if (this.predictionScore >= 100) {
            this.predictionTier = PredictionTier.BRONZE;
        } else if (this.predictionScore >= 50) {
            this.predictionTier = PredictionTier.IRON;
        } else {
            this.predictionTier = PredictionTier.NONE;
        }
    }
}