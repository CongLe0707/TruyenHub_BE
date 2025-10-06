package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateStoryReq;
import com.example.TruyenHub.dto.res.CreateStoryRes;
import com.example.TruyenHub.dto.res.DetailStoryRes;
import com.example.TruyenHub.dto.res.ListStoryRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.outfras.repo.AuthorRepository;
import com.example.TruyenHub.outfras.repo.CategoryRepository;
import com.example.TruyenHub.outfras.repo.StoryRepository;
import com.example.TruyenHub.mapper.StoryMapper;
import com.example.TruyenHub.model.entity.Author;
import com.example.TruyenHub.model.entity.Category;
import com.example.TruyenHub.model.entity.Story;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final StoryMapper storyMapper;


    @Override
    public CreateStoryRes createNovel(CommonReq<CreateStoryReq> req) {
        CreateStoryReq data = req.getData();
        Author author = authorRepository.findByName(data.authorName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_AUTHOR.getCode(),
                        ResultCode.NO_AUTHOR.getMessage())
                );
        Category category = categoryRepository.findByName(data.categoriName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_CATEGORY.getCode(),
                        ResultCode.NO_CATEGORY.getMessage())
                );
        Story story = storyMapper.toEntity(data);
        story.setAuthor(author);
        story.setCategory(category);
        story.setCreatedAt(LocalDateTime.now());
        story.setUpdatedAt(LocalDateTime.now());

        Story saved = storyRepository.save(story);


        return new CreateStoryRes(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCategory().getName(),
                saved.getAuthor().getName(),
                saved.getCreatedAt()

        );
    }

    @Override
    public ListStoryRes listStory() {
        List<ListStoryRes.StoryDtoList> stories = storyRepository.findAll()
                .stream()
                .map(story -> new ListStoryRes.StoryDtoList(
                        story.getId(),
                        story.getTitle(),
                        story.getDescription(),
                        story.getCategory().getName(),
                        story.getAuthor().getName(),
                        story.getCreatedAt()
                ))
                .toList();
        return new ListStoryRes(stories);
    }


    @Override
    public DetailStoryRes detailStory(UUID id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                    ResultCode.NO_STORY_NAME.getCode(),
                    ResultCode.NO_STORY_NAME.getMessage())
        );
        return new DetailStoryRes (story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getCategory().getName(),
                story.getAuthor().getName(),
                story.getCreatedAt(),
                story.getChapter().stream()
                        .map(ch -> new DetailStoryRes.ChapterStoryDto(
                                ch.getId(),
                                ch.getChapterNumber(),
                                ch.getTitle(),
                                ch.getCreatedAt()
                        ))
                        .toList()
        );

    }
}
