package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CreateChapterComicReq;
import com.example.TruyenHub.dto.res.ChapterComicDetailRes;
import com.example.TruyenHub.dto.res.CreateChapterComicRes;

import java.util.UUID;

public interface ChapterComicService {
    CreateChapterComicRes createChapterComic (CreateChapterComicReq req);

    ChapterComicDetailRes detailChapterComic(UUID id);
}
