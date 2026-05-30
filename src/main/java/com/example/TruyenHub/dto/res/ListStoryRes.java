package com.example.TruyenHub.dto.res;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListStoryRes (
        List<StoryDtoList> storyDtoLists
) {
    public record StoryDtoList(
            UUID id,
            String title,
            String description,
            String coverImage,
            String categoryName,
            String authorName,
            Float avrRating,
            LocalDateTime createdAt
    ) {
    }
}
