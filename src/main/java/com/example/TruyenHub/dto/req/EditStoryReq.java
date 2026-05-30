package com.example.TruyenHub.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public record EditStoryReq(
        UUID id,
        String title,
        String description,
        @JsonIgnore
        MultipartFile coverImage,
        String categoryName,
        String authorName
) {
}
