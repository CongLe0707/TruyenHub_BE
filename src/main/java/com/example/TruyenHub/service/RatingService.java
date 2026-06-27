package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.RatingReq;

import com.example.TruyenHub.dto.res.UserRatingRes;

import java.util.UUID;

public interface RatingService {
    String rateStory(CommonReq<RatingReq> req);
    String rateComic(CommonReq<RatingReq> req);
    UserRatingRes getUserStoryRating(UUID storyId);
    UserRatingRes getUserComicRating(UUID comicId);
}
