package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommentReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.CommentRes;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    String addStoryComment(CommonReq<CommentReq> req);
    String addComicComment(CommonReq<CommentReq> req);
    List<CommentRes> getStoryComments(UUID storyId);
    List<CommentRes> getComicComments(UUID comicId);
}
