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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import static com.example.TruyenHub.utils.ImgUtils.saveFile;


@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final StoryMapper storyMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public CreateStoryRes createNovel(CommonReq<CreateStoryReq> req) {
        CreateStoryReq data = req.getData();
        Author author = authorRepository.findByName(data.authorName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_AUTHOR.getCode(),
                        ResultCode.NO_AUTHOR.getMessage())
                );
        Category category = categoryRepository.findByName(data.categoryName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_CATEGORY.getCode(),
                        ResultCode.NO_CATEGORY.getMessage())
                );
        Story story = storyMapper.toEntity(data);
        updateStory(story,author,category);
        StoryCoverImage(story, data.coverImage());
        Story saved = storyRepository.save(story);

        return new CreateStoryRes(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCoverImage(),
                saved.getCategory().getName(),
                saved.getAuthor().getName(),
                saved.getAvrRating(),
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
                        story.getCoverImage(),
                        story.getCategory().getName(),
                        story.getAuthor().getName(),
                        story.getAvrRating(),
                        story.getCreatedAt()
                ))
                .toList();
        return new ListStoryRes(stories);
    }


    @Override
    @jakarta.transaction.Transactional
    public DetailStoryRes detailStory(UUID id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                    ResultCode.NO_STORY_NAME.getCode(),
                    ResultCode.NO_STORY_NAME.getMessage())
        );
        return new DetailStoryRes (story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getCoverImage(),
                story.getCategory().getName(),
                story.getAuthor().getName(),
                story.getAvrRating(),
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

    private void updateStory(Story story, Author author, Category category){
        story.setAuthor(author);
        story.setCategory(category);
        story.setCreatedAt(LocalDateTime.now());
        story.setUpdatedAt(LocalDateTime.now());
    }

    private void StoryCoverImage(Story story, MultipartFile coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            try {
                String filePath = saveFile(coverImage, "Story", uploadDir);
                story.setCoverImage(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi lưu ảnh bìa: " + coverImage.getOriginalFilename(), e);
            }
        }
    }

    @Override
    @jakarta.transaction.Transactional
    public CreateStoryRes editStory(CommonReq<com.example.TruyenHub.dto.req.EditStoryReq> req) {
        com.example.TruyenHub.dto.req.EditStoryReq data = req.getData();
        Story story = storyRepository.findById(data.id())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));

        Author author = authorRepository.findByName(data.authorName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_AUTHOR.getCode(),
                        ResultCode.NO_AUTHOR.getMessage())
                );
        Category category = categoryRepository.findByName(data.categoryName())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_CATEGORY.getCode(),
                        ResultCode.NO_CATEGORY.getMessage())
                );

        story.setTitle(data.title());
        story.setDescription(data.description());
        if (data.coverImage() != null && !data.coverImage().isEmpty()) {
            StoryCoverImage(story, data.coverImage());
        }
        story.setAuthor(author);
        story.setCategory(category);
        story.setUpdatedAt(LocalDateTime.now());

        return new CreateStoryRes(
                story.getId(),
                story.getTitle(),
                story.getDescription(),
                story.getCoverImage(),
                story.getCategory().getName(),
                story.getAuthor().getName(),
                story.getAvrRating(),
                story.getCreatedAt()
        );
    }

    @Override
    @jakarta.transaction.Transactional
    public void deleteStory(UUID id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage()
                ));
        storyRepository.delete(story);
    }
}
