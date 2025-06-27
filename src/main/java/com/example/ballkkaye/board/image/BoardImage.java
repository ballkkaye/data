package com.example.ballkkaye.board.image;

import com.example.ballkkaye.board.Board;
import com.example.ballkkaye.common.enums.DeleteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "board_image_tb")
@Entity
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeleteStatus deleteStatus;

    @Column(nullable = false)
    private String imgUrl;
}