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

import com.example.TruyenHub.dto.res.UserRatingRes;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private Story retriveStory(UUID id) {
        return storyRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));
    }

    private Comic retriveComic(UUID id) {
        return comicRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage()
                ));
    }

    @Override
    public String rateStory(CommonReq<RatingReq> req) {
        User user = getCurrentUser();
        RatingReq data = req.getData();

        Story story = retriveStory(data.targetId());

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
    public String rateComic(CommonReq<RatingReq> req) {
        User user = getCurrentUser();
        RatingReq data = req.getData();
        Comic comic = retriveComic(data.targetId());
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
    public UserRatingRes getUserStoryRating(UUID storyId) {
        User user = getCurrentUser();
        Story story = retriveStory(storyId);
        Float userRating = storyRatingRepository.findByUserAndStory(user, story)
                .map(StoryRating::getRating)
                .orElse(null);
        return new UserRatingRes(story.getAvrRating(), userRating);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRatingRes getUserComicRating(UUID comicId) {
        User user = getCurrentUser();
        Comic comic = retriveComic(comicId);
        Float userRating = comicRatingRepository.findByUserAndComic(user, comic)
                .map(ComicRating::getRating)
                .orElse(null);
        return new UserRatingRes(comic.getAvrRating(), userRating);
    }
}
