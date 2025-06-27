package com.example.ballkkaye.chat.room;

import com.example.ballkkaye.common.enums.Age;
import com.example.ballkkaye.common.enums.DeleteStatus;
import com.example.ballkkaye.common.enums.Gender;
import com.example.ballkkaye.common.enums.Region;
import com.example.ballkkaye.game.Game;
import com.example.ballkkaye.team.Team;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "chat_room_tb")
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private Gender preferredGender;

    @Column
    private Age preferredAge;

    @Column
    private Region preferredRegion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    @Column
    private Timestamp lastDisconnectedAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreationTimestamp
    private Timestamp createdAt;
}