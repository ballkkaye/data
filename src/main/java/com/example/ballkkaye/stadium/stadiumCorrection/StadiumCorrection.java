package com.example.ballkkaye.stadium.stadiumCorrection;

import com.example.ballkkaye.stadium.Stadium;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "stadium_correction_tb")
@Entity
public class StadiumCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @Column(nullable = false)
    private Double correction;

    @Column(nullable = false)
    private Integer thisYear;
}