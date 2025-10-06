package com.example.TruyenHub.outfras.repo;

import com.example.TruyenHub.model.entity.ChapterComic;
import com.example.TruyenHub.model.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChapterComicRepository extends JpaRepository<ChapterComic, UUID> {

    boolean existsByComicAndChapterNumber(Comic comic, Integer chapterNumber);

    boolean existsByComicAndTitle(Comic comic, String title);


}
