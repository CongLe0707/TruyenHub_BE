package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.ChapterComic;
import com.example.TruyenHub.model.entity.ChapterImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChapterImageRepository extends JpaRepository<ChapterImage, UUID> {
    boolean existsByChapterComicAndImageUrl(ChapterComic chapterComic, String imageUrl);
}
