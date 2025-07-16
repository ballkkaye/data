package com.example.ballkkaye.board.reply.like;

import com.example.ballkkaye.board.reply.BoardReply;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "board_reply_like_tb")
@Entity
public class BoardReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_reply_id")
    private BoardReply boardReply;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createTime;

    public BoardReplyLike(BoardReply boardReply, User user) {
        this.boardReply = boardReply;
        this.user = user;
    }
}
