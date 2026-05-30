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
@Table(name = "comics")
@Getter
@Setter
public class Comic {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "cover_image", length = 255)
    private String coverImage;

    @Column(name = "rating")
    private Float  avrRating;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", length = 20, nullable = false)
//    private StoryStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;


    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ChapterComic> chapterComics;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "type", length = 20, nullable = false)
//    private CommicType type;


}
