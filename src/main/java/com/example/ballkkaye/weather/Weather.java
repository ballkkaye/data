package com.example.ballkkaye.weather;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.stadium.Stadium;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "weather_tb")
@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double windSpeed;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WindDirection windDirection;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WFCD weatherCode;

    @Column(nullable = false)
    private Double rainPer;

    @Column(nullable = false)
    private Double humidityPer;

    @Column(nullable = false)
    private Timestamp forecastAt;

    @Column(nullable = false)
    private Double rainAmount;

    @Column(nullable = false)
    private Double rainoutPer;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Weather(Integer id, Game game, Stadium stadium, Double temperature, Double windSpeed, WindDirection windDirection, WFCD weatherCode, Double rainPer, Double humidityPer, Timestamp forecastAt, Double rainAmount, Double rainoutPer, Timestamp createdAt) {
        this.id = id;
        this.game = game;
        this.stadium = stadium;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.weatherCode = weatherCode;
        this.rainPer = rainPer;
        this.humidityPer = humidityPer;
        this.forecastAt = forecastAt;
        this.rainAmount = rainAmount;
        this.rainoutPer = rainoutPer;
        this.createdAt = createdAt;
    }
}