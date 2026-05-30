package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.AuthorReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<CommonRes> createAuthor (@RequestBody CommonReq<AuthorReq> req) {
       return ApiHandler.handle(req,authorService::createAuthor);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonRes> listAuthor() {
        return ApiHandler.handle(null, req -> authorService.listAuthor());
    }
}
