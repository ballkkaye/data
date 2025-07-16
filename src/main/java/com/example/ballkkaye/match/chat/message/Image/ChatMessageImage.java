package com.example.ballkkaye.match.chat.message.Image;

import com.example.ballkkaye.match.chat.message.ChatMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "chat_message_image_tb")
@Entity
public class ChatMessageImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ChatMessage chatMessage;

    @Column(nullable = false)
    private String imageUrl;

    @CreationTimestamp
    private Timestamp createdAt;
}