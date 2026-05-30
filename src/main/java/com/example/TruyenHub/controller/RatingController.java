package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.RatingReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/story")
    public ResponseEntity<CommonRes> rateStory(@RequestBody CommonReq<RatingReq> req) {
        return ApiHandler.handle(req, ratingService::rateStory);
    }

    @PostMapping("/comic")
    public ResponseEntity<CommonRes> rateComic(@RequestBody CommonReq<RatingReq> req) {
        return ApiHandler.handle(req, ratingService::rateComic);
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<CommonRes> getUserStoryRating(@PathVariable UUID id) {
        var data = ratingService.getUserStoryRating(id);
        return ResponseEntity.ok(new CommonRes<>(data));
    }

    @GetMapping("/comic/{id}")
    public ResponseEntity<CommonRes> getUserComicRating(@PathVariable UUID id) {
        var data = ratingService.getUserComicRating(id);
        return ResponseEntity.ok(new CommonRes<>(data));
    }
}
