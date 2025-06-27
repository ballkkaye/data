package com.example.ballkkaye.board;

import com.example.ballkkaye.common.enums.DeleteStatus;
import com.example.ballkkaye.team.Team;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeleteStatus deleteStatus;


    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreationTimestamp
    private Timestamp createdAt;
}