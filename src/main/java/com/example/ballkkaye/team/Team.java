package com.example.ballkkaye.team;

import com.example.ballkkaye.stadium.Stadium;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "team_tb")
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stadium_id1", nullable = false)
    private Stadium stadiumId1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id2")
    private Stadium stadiumId2;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Column(name = "ticket_link")
    private String ticketLink;
}
