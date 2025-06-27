package com.example.ballkkaye.stadium;

import com.example.ballkkaye.common.enums.StadiumType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "stadium_tb")
@Entity
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stadium_name", nullable = false)
    private String stadiumName;

    @Column(nullable = false)
    private String location;

    @Column(name = "stadium_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StadiumType stadiumType;
}