package com.example.TruyenHub.model.entity;

import com.example.TruyenHub.model.enums.CommicType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stories")
@Getter
@Setter
public class Story {
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
    private Float avrRating;

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

    @OneToMany(mappedBy = "story",  fetch = FetchType.EAGER)
    private List<Chapter> chapter;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "type", length = 20, nullable = false)
//    private CommicType type;

}
