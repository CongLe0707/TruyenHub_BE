package com.example.TruyenHub.model.entity;

import com.example.TruyenHub.anotation.DatePattern;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "chapter_comic")
@Getter
@Setter
public class ChapterComic {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "chapter_number", nullable = false)
    private Integer chapterNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "comic_id", nullable = false)
    private Comic comic;

    // Một chap có nhiều ảnh
    @OneToMany(mappedBy = "chapterComic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChapterImage> images;

}
