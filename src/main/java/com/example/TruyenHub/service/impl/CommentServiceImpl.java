package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommentReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.CommentRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.entity.Comment;
import com.example.TruyenHub.model.entity.Story;
import com.example.TruyenHub.model.entity.User;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.outfras.repo.ComicRepository;
import com.example.TruyenHub.outfras.repo.CommentRepository;
import com.example.TruyenHub.outfras.repo.StoryRepository;
import com.example.TruyenHub.outfras.repo.UserRepository;
import com.example.TruyenHub.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
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
    public String addStoryComment(CommonReq<CommentReq> req) {
        User user = getCurrentUser();
        CommentReq data = req.getData();

        Story story = storyRepository.findById(data.targetId())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setStory(story);
        comment.setContent(data.content());
        comment.setCreatedAt(LocalDateTime.now());

        if (data.parentId() != null) {
            Comment parent = commentRepository.findById(data.parentId())
                    .orElseThrow(() -> new DelegationServiceException(
                            ResultCode.NO_STORY_NAME.getCode(),
                            ResultCode.NO_STORY_NAME.getMessage()
                    ));
            comment.setParentComment(parent);
        }

        commentRepository.save(comment);
        return  ResultCode.CMT_SUCCESS.getMessage();
    }

    @Override
    @Transactional
    public String addComicComment(CommonReq<CommentReq> req) {
        User user = getCurrentUser();
        CommentReq data = req.getData();

        Comic comic = comicRepository.findById(data.targetId())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage()
                ));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComic(comic);
        comment.setContent(data.content());
        comment.setCreatedAt(LocalDateTime.now());

        if (data.parentId() != null) {
            Comment parent = commentRepository.findById(data.parentId())
                    .orElseThrow(() -> new DelegationServiceException(
                            ResultCode.NO_STORY_NAME.getCode(),
                            ResultCode.NO_STORY_NAME.getMessage()
                    ));
            comment.setParentComment(parent);
        }

        commentRepository.save(comment);
        return ResultCode.CMT_SUCCESS.getMessage();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentRes> getStoryComments(UUID storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));
        List<Comment> roots = commentRepository.findByStoryAndParentCommentIsNullOrderByCreatedAtDesc(story);
        return roots.stream().map(this::mapToRes).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentRes> getComicComments(UUID comicId) {
        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage()
                ));
        List<Comment> roots = commentRepository.findByComicAndParentCommentIsNullOrderByCreatedAtDesc(comic);
        return roots.stream().map(this::mapToRes).toList();
    }

    private CommentRes mapToRes(Comment comment) {
        return new CommentRes(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUserName(),
                null, // user avatar if exists
                comment.getCreatedAt(),
                comment.getReplies().stream().map(this::mapToRes).toList()
        );
    }
}
