package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateChapterReq;
import com.example.TruyenHub.dto.res.CreateChapterRes;
import com.example.TruyenHub.dto.res.DetailChapterStoryRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.outfras.repo.ChapterRepository;
import com.example.TruyenHub.outfras.repo.StoryRepository;
import com.example.TruyenHub.mapper.ChapterMapper;
import com.example.TruyenHub.model.entity.Chapter;
import com.example.TruyenHub.model.entity.Story;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;
    private final ChapterRepository chapterRepository;
    private final StoryRepository storyRepository;

    @Override
    public CreateChapterRes createChapter(CommonReq<CreateChapterReq> req) {
        CreateChapterReq data = req.getData();

        Story story = storyRepository.findByTitle(data.storyName())
                .orElseThrow(() -> new DelegationServiceException(
                    ResultCode.NO_STORY_NAME.getCode(),
                    ResultCode.NO_STORY_NAME.getMessage())
        );

        Chapter chapter = chapterMapper.toEntity(data);
        chapter.setStory(story);

        // Chuẩn hóa Unicode (từ Tổ hợp NFD sang Dựng sẵn NFC) để fix lỗi font Tiếng Việt
        if (chapter.getContent() != null) {
            chapter.setContent(java.text.Normalizer.normalize(chapter.getContent(), java.text.Normalizer.Form.NFC));
        }
        if (chapter.getTitle() != null) {
            chapter.setTitle(java.text.Normalizer.normalize(chapter.getTitle(), java.text.Normalizer.Form.NFC));
        }

        Chapter saved = chapterRepository.save(chapter);

        return new CreateChapterRes (
                saved.getId(),
                saved.getStory().getTitle(),
                saved.getTitle(),
                saved.getChapterNumber(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    @Override
    public DetailChapterStoryRes detailChapterStory(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_STORY_NAME.getCode(),
                        ResultCode.NO_STORY_NAME.getMessage())
                );

        return new DetailChapterStoryRes (
                chapter.getId(),
                chapter.getStory().getTitle(),
                chapter.getTitle(),
                chapter.getChapterNumber(),
                chapter.getContent(),
                chapter.getCreatedAt()
        );
    }
}
