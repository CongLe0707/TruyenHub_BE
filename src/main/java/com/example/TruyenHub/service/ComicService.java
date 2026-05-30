package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateComicReq;
import com.example.TruyenHub.dto.res.DetailComicRes;
import com.example.TruyenHub.dto.res.CreateComicRes;
import com.example.TruyenHub.dto.res.ListComicRes;

import java.util.UUID;

public interface ComicService {

    CreateComicRes createComic(CommonReq<CreateComicReq> req);

    DetailComicRes detailComic(UUID comicId);

    ListComicRes listComic();

    CreateComicRes editComic(CommonReq<com.example.TruyenHub.dto.req.EditComicReq> req);

    void deleteComic(UUID id);
}
