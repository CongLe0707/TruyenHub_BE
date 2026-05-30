package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CategoryReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateChapterReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chapter")
public class ChapterController {
    private  final ChapterService chapterService;

    @PostMapping("/create")
    public ResponseEntity<CommonRes> createChapter(@RequestBody CommonReq<CreateChapterReq> req) {
        return ApiHandler.handle(req,chapterService::createChapter);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CommonRes> detailChapter(@PathVariable UUID id) {
        return  ApiHandler.handle(null, req -> chapterService.detailChapterStory(id));
    }
}

