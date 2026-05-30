package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.dto.res.CreateStoryRes;
import com.example.TruyenHub.dto.res.DetailStoryRes;
import com.example.TruyenHub.dto.res.ListStoryRes;

import java.util.UUID;

public interface StoryService {

    CreateStoryRes createNovel(CommonReq<CreateStoryReq> req);

    ListStoryRes listStory();

    DetailStoryRes detailStory (UUID id);

    CreateStoryRes editStory(CommonReq<com.example.TruyenHub.dto.req.EditStoryReq> req);

    void deleteStory(UUID id);
}
