package com.example.TruyenHub.dto.res;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CommentRes(
        UUID id,
        String content,
        String userName,
        String userAvatar,
        LocalDateTime createdAt,
        List<CommentRes> replies
) {}
