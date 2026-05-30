package com.example.TruyenHub.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "chapter_image")
@Getter
@Setter
public class ChapterImage {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private ChapterComic chapterComic;

}
