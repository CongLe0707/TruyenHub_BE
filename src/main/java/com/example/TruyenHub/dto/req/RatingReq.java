package com.example.TruyenHub.dto.req;

import java.util.UUID;

public record RatingReq(
        UUID targetId, // storyId or comicId
        Float rating
) {}
