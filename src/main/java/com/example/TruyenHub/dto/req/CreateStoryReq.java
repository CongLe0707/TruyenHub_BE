package com.example.TruyenHub.dto.req;

import com.example.TruyenHub.model.entity.Author;
import com.example.TruyenHub.model.entity.Category;
import com.example.TruyenHub.model.entity.Chapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public record CreateStoryReq (
        String title,
        String description,
        @JsonIgnore
        MultipartFile coverImage,
        String categoryName,
        String authorName

) {
}
