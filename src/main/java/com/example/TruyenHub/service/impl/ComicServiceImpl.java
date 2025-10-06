package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateComicReq;
import com.example.TruyenHub.dto.res.DetailComicRes;
import com.example.TruyenHub.dto.res.CreateComicRes;
import com.example.TruyenHub.dto.res.ListComicRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.outfras.repo.AuthorRepository;
import com.example.TruyenHub.outfras.repo.CategoryRepository;
import com.example.TruyenHub.outfras.repo.ComicRepository;
import com.example.TruyenHub.mapper.ComicMapper;
import com.example.TruyenHub.model.entity.Author;
import com.example.TruyenHub.model.entity.Category;
import com.example.TruyenHub.model.entity.Comic;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.service.ComicService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComicServiceImpl implements ComicService {

    private final ComicRepository comicRepository;
    private final ComicMapper comicMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public CreateComicRes createComic(CommonReq<CreateComicReq> req) {
        CreateComicReq data = req.getData();
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
        Comic comic = comicMapper.toEntity(data);

        comic.setAuthor(author);
        comic.setCategory(category);
        comic.setCreatedAt(LocalDateTime.now());
        comic.setUpdatedAt(LocalDateTime.now());

        ComicCoverImage(comic, data.coverImage());

        Comic saved = comicRepository.save(comic);
        return new CreateComicRes(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCategory().getName(),
                saved.getAuthor().getName(),
                saved.getCreatedAt()

        );
    }

//Chi tiết truyện
    @Transactional
    @Override
    public DetailComicRes detailComic(UUID comicId) {

        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage())
                );

        return new DetailComicRes(
                comic.getId(),
                comic.getTitle(),
                comic.getDescription(),
                comic.getCoverImage(),
                comic.getCategory().getName(),
                comic.getAuthor().getName(),
                comic.getCreatedAt(),
                comic.getChapterComics().stream()
                        .map(ch -> new DetailComicRes.ChapterComicDto(
                                ch.getId(),
                                ch.getChapterNumber(),
                                ch.getTitle(),
                                ch.getCreatedAt()
                        ))
                        .toList()
        );
    }


    @Override
    public ListComicRes listComic() {
        List<ListComicRes.ComicDtoList> comicDtos = comicRepository.findAll().stream()
                .map(comic -> new ListComicRes.ComicDtoList(
                        comic.getId(),
                        comic.getTitle(),
                        comic.getDescription(),
                        comic.getCategory().getName(),
                        comic.getAuthor().getName(),
                        comic.getCreatedAt()
                ))
                .toList();
        return new ListComicRes(comicDtos);
    }

    private String saveFile(MultipartFile file, String folderName) throws IOException {
        Path folderPath = Paths.get(uploadDir, folderName);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename());
        Path filePath = folderPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Trả về relative path (để dùng API load ảnh tĩnh)
        return "/uploads/ComicCoverImage/" + folderName + "/" + fileName;
    }

    private void ComicCoverImage(Comic comic, MultipartFile coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            try {
                String filePath = saveFile(coverImage, "Comic");
                comic.setCoverImage(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi lưu ảnh bìa: " + coverImage.getOriginalFilename(), e);
            }
        }
    }



}
