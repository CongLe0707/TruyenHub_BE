package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.http.MediaType;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/story")
public class StoryController {

    private final StoryService storyService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonRes> createNovel(@ModelAttribute CreateStoryReq req) {
        return ApiHandler.handle(new CommonReq<>(req), storyService::createNovel);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonRes> GetAllStory() {
        return ApiHandler.handle(null, req -> storyService.listStory());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CommonRes> GetDetail(@PathVariable UUID id) {
        return ApiHandler.handle(null, req -> storyService.detailStory(id));
    }

    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonRes> editStory(@ModelAttribute com.example.TruyenHub.dto.req.EditStoryReq req) {
        return ApiHandler.handle(new CommonReq<>(req), storyService::editStory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonRes> deleteStory(@PathVariable UUID id) {
        return ApiHandler.handle(new CommonReq<>(), req -> {
            storyService.deleteStory(id);
            return "Deleted successfully";
        });
    }
}
