package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.RatingReq;

import java.util.Map;
import java.util.UUID;

public interface RatingService {
    String rateStory(CommonReq<RatingReq> req);
    String rateComic(CommonReq<RatingReq> req);
    Map<String, Object> getUserStoryRating(UUID storyId);
    Map<String, Object> getUserComicRating(UUID comicId);
}
