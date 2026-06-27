package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CreateComicReq;
import com.example.TruyenHub.dto.res.DetailComicRes;
import com.example.TruyenHub.dto.res.CreateComicRes;
import com.example.TruyenHub.dto.res.ListComicRes;

import java.util.UUID;

public interface ComicService {

    CreateComicRes createComic(CreateComicReq req);

    DetailComicRes detailComic(UUID comicId);

    ListComicRes listComic();

    CreateComicRes editComic(com.example.TruyenHub.dto.req.EditComicReq req);

    String deleteComic(UUID id);
}
