package com.example.TruyenHub.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "comic_rating", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "comic_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComicRating {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private Comic comic;

    @Column(name = "rating")
    private float rating;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

}
