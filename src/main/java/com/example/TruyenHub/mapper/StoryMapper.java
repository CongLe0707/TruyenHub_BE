package com.example.TruyenHub.mapper;

import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.model.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    @Mapping(target = "id", ignore = true)                // id do DB generate
    //@Mapping(target = "status", constant = "ONGOING")     // default status
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "author", ignore = true)            // set sau trong service
    @Mapping(target = "category", ignore = true)          // set sau trong service
    @Mapping(target = "chapter", ignore = true)           // set sau nếu cần
    @Mapping(target = "coverImage", ignore = true)
    Story toEntity(CreateStoryReq req);
}
