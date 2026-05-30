package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.RatingReq;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.entity.ComicRating;
import com.example.TruyenHub.model.entity.Story;
import com.example.TruyenHub.model.entity.StoryRating;
import com.example.TruyenHub.model.entity.User;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.outfras.repo.ComicRatingRepository;
import com.example.TruyenHub.outfras.repo.ComicRepository;
import com.example.TruyenHub.outfras.repo.StoryRatingRepository;
import com.example.TruyenHub.outfras.repo.StoryRepository;
import com.example.TruyenHub.outfras.repo.UserRepository;
import com.example.TruyenHub.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final StoryRatingRepository storyRatingRepository;
    private final ComicRatingRepository comicRatingRepository;
    private final StoryRepository storyRepository;
    private final ComicRepository comicRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_USER.getCode(),
                        ResultCode.NO_USER.getMessage()
                ));
    }

    @Override
    @Transactional
    public String rateStory(CommonReq<RatingReq> req) {
        User user = getCurrentUser();
        RatingReq data = req.getData();

        Story story = storyRepository.findById(data.targetId())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));

        StoryRating rating = storyRatingRepository.findByUserAndStory(user, story)
                .orElseGet(() -> {
                    StoryRating newRating = new StoryRating();
                    newRating.setUser(user);
                    newRating.setStory(story);
                    newRating.setCreatedAt(LocalDateTime.now());
                    return newRating;
                });
        
        rating.setRating(data.rating());
        storyRatingRepository.save(rating);

        Float avg = storyRatingRepository.getAverageRatingByStory(story);
        story.setAvrRating(avg != null ? avg : data.rating());
        storyRepository.save(story);

        return ResultCode.SUCCESS_RATING.getMessage();
    }

    @Override
    @Transactional
    public String rateComic(CommonReq<RatingReq> req) {
        User user = getCurrentUser();
        RatingReq data = req.getData();

        Comic comic = comicRepository.findById(data.targetId())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage()
                ));

        ComicRating rating = comicRatingRepository.findByUserAndComic(user, comic)
                .orElseGet(() -> {
                    ComicRating newRating = new ComicRating();
                    newRating.setUser(user);
                    newRating.setComic(comic);
                    newRating.setCreatedAt(LocalDateTime.now());
                    return newRating;
                });
        
        rating.setRating(data.rating());
        comicRatingRepository.save(rating);

        Float avg = comicRatingRepository.getAverageRatingByComic(comic);
        comic.setAvrRating(avg != null ? avg : data.rating());
        comicRepository.save(comic);

        return ResultCode.SUCCESS_RATING.getMessage();
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getUserStoryRating(java.util.UUID storyId) {
        User user = getCurrentUser();
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("averageRating", story.getAvrRating());
        storyRatingRepository.findByUserAndStory(user, story)
                .ifPresent(r -> result.put("userRating", r.getRating()));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Map<String, Object> getUserComicRating(java.util.UUID comicId) {
        User user = getCurrentUser();
        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage()
                ));
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("averageRating", comic.getAvrRating());
        comicRatingRepository.findByUserAndComic(user, comic)
                .ifPresent(r -> result.put("userRating", r.getRating()));
        return result;
    }
}
