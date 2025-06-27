package com.example.ballkkaye.visitRecord.Image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "visit_record_image_tb")
@Entity
public class VisitRecordImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer visitRecordId;

    @Column(nullable = false)
    private String imageUrl;

    @CreationTimestamp
    private Timestamp createdAt;
}