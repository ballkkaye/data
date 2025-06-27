package com.example.ballkkaye.user.userMatch;

import com.example.ballkkaye.chat.room.ChatRoom;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "user_match_tb")
@Entity
public class UserMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column
    private Timestamp updatedAt;

    @CreationTimestamp
    private Timestamp createdAt;
}