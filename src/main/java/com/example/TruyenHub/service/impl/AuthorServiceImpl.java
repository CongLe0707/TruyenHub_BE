package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.AuthorReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.res.AuthorRes;
import com.example.TruyenHub.outfras.repo.AuthorRepository;
import com.example.TruyenHub.mapper.AuthorMapper;
import com.example.TruyenHub.model.entity.Author;
import com.example.TruyenHub.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public  class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorRes createAuthor(CommonReq<AuthorReq> req) {
        Author author  =  authorRepository.save(authorMapper.toEntity(req.getData()));
        return new AuthorRes (
                author.getId(),
                author.getName(),
                author.getBio()
        );
    }
    @Override
    public List<AuthorRes> listAuthor() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorRes(author.getId(), author.getName(), author.getBio()))
                .toList();
    }
}
