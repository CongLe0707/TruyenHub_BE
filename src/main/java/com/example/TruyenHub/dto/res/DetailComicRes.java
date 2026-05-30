package com.example.TruyenHub.dto.res;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DetailComicRes(
        UUID id,
        String title,
        String description,
        String coverImage,
        String category,
        String author,
        Float avtRating,
        LocalDateTime createdAt,
        List<ChapterComicDto> chapterComic
){
    public record ChapterComicDto(
            UUID id,
            Integer numberChapter,
            String title,
            LocalDateTime createdAt
    ) {}
}
