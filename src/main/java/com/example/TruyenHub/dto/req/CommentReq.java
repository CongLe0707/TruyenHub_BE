package com.example.TruyenHub.dto.req;

import java.util.UUID;

public record CommentReq(
        UUID targetId, // storyId or comicId
        String content,
        UUID parentId // null if it's a root comment
) {}
