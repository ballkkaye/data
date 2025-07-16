package com.example.ballkkaye.board.like;

import com.example.ballkkaye.board.Board;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "board_like_tb")
@Entity
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
