package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CommentReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/story")
    public ResponseEntity<CommonRes> addStoryComment(@RequestBody CommonReq<CommentReq> req) {
        return ApiHandler.handle(req, commentService::addStoryComment);
    }

    @PostMapping("/comic")
    public ResponseEntity<CommonRes> addComicComment(@RequestBody CommonReq<CommentReq> req) {
        return ApiHandler.handle(req, commentService::addComicComment);
    }

    @GetMapping("/story/{id}")
    public ResponseEntity<CommonRes> getStoryComments(@PathVariable UUID id) {
        var data = commentService.getStoryComments(id);
        return ResponseEntity.ok(new CommonRes<>(data));
    }

    @GetMapping("/comic/{id}")
    public ResponseEntity<CommonRes> getComicComments(@PathVariable UUID id) {
        var data = commentService.getComicComments(id);
        return ResponseEntity.ok(new CommonRes<>(data));
    }
}
