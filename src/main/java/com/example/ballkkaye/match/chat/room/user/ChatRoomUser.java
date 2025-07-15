package com.example.ballkkaye.match.chat.room.user;

import com.example.ballkkaye.common.enums.DeleteStatus;
import com.example.ballkkaye.match.chat.room.ChatRoom;
import com.example.ballkkaye.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "chat_room_user_tb")
@Entity
public class ChatRoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status", nullable = false)
    private DeleteStatus deleteStatus;

    @Column(nullable = false)
    private Boolean isOwner;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public ChatRoomUser(ChatRoom chatRoom, User user, Boolean isOwner, DeleteStatus deleteStatus) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.isOwner = isOwner;
        this.deleteStatus = deleteStatus;
    }

    public void delete() {
        this.deleteStatus = DeleteStatus.DELETED;
    }

    public void setDeleteStatus(DeleteStatus deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}