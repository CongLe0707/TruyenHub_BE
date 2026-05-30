package com.example.TruyenHub.dto.res;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListComicRes (
        List<ComicDtoList> comicDtoList
) {
    public  record ComicDtoList(
            UUID id,
            String title,
            String description,
            String coverImage,
            String categoryName,
            String authorName,
            Float avtRating,
            LocalDateTime createdAt
    ) {}

}
