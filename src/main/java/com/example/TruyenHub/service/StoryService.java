package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.dto.res.CreateStoryRes;
import com.example.TruyenHub.dto.res.DetailStoryRes;
import com.example.TruyenHub.dto.res.ListStoryRes;

import java.util.UUID;

public interface StoryService {

    CreateStoryRes createNovel(CreateStoryReq req);

    ListStoryRes listStory();

    DetailStoryRes detailStory (UUID id);

    CreateStoryRes editStory(com.example.TruyenHub.dto.req.EditStoryReq req);

    String deleteStory(UUID id);
}
