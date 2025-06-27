package com.example.ballkkaye.weather;

import com.example.ballkkaye.common.enums.WFCD;
import com.example.ballkkaye.common.enums.WindDirection;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.stadium.Stadium;
import jakarta.persistence.*;
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
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
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
}