package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.AuthorReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.AuthorRes;
import  java.util.List;

public interface AuthorService {
    AuthorRes createAuthor(CommonReq<AuthorReq> req);
    List<AuthorRes> listAuthor();
}
