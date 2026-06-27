package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.CreateComicReq;
import com.example.TruyenHub.dto.res.CategoryRes;
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

import static com.example.TruyenHub.utils.ImgUtils.saveFile;

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
    public CreateComicRes createComic(CreateComicReq data) {

        Author author = retriveAuthor(data.authorName());

        Category category = retriveCategory(data.categoryName());

        Comic comic = comicMapper.toEntity(data);

        updateAuthor(comic, author, category);

        ComicCoverImage(comic, data.coverImage());

        comicRepository.save(comic);
        return new CreateComicRes(
                comic.getId(),
                comic.getTitle(),
                comic.getDescription(),
                comic.getCategory().getName(),
                comic.getAuthor().getName(),
                comic.getAvrRating(),
                comic.getCreatedAt()

        );

    }

//Chi tiết truyện
    @Override
    public DetailComicRes detailComic(UUID comicId) {

        Comic comic = retriveComic(comicId);

        return new DetailComicRes(
                comic.getId(),
                comic.getTitle(),
                comic.getDescription(),
                comic.getCoverImage(),
                comic.getCategory().getName(),
                comic.getAuthor().getName(),
                comic.getAvrRating(),
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
                        comic.getCoverImage(),
                        comic.getCategory().getName(),
                        comic.getAuthor().getName(),
                        comic.getAvrRating(),
                        comic.getCreatedAt()
                ))
                .toList();
        return new ListComicRes(comicDtos);
    }



    private void ComicCoverImage(Comic comic, MultipartFile coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            try {
                String filePath = saveFile(coverImage, "Comic", uploadDir);
                comic.setCoverImage(filePath); //?
            } catch (IOException e) {
                throw new RuntimeException("Lỗi lưu ảnh bìa: " + coverImage.getOriginalFilename(), e);
            }
        }
    }


    private Author retriveAuthor(String author) {
         return authorRepository.findByName(author)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_AUTHOR.getCode(),
                        ResultCode.NO_AUTHOR.getMessage())
                );
    }

    private Category retriveCategory(String categoryName){
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_CATEGORY.getCode(),
                        ResultCode.NO_CATEGORY.getMessage())
                );

    }

    private void updateAuthor(Comic comic,Author author, Category category) {
        comic.setAuthor(author);
        comic.setCategory(category);
        comic.setCreatedAt(LocalDateTime.now());
        comic.setUpdatedAt(LocalDateTime.now());

    }
    
    private Comic retriveComic(UUID comicId) {
        return comicRepository.findById(comicId)
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.NO_COMIC_ID.getCode(),
                        ResultCode.NO_COMIC_ID.getMessage())
                );
    }
    
    @Override
    public CreateComicRes editComic(com.example.TruyenHub.dto.req.EditComicReq data) {
        Comic comic = retriveComic(data.id());

        Author author = retriveAuthor(data.authorName());
        Category category = retriveCategory(data.categoryName());

        comic.setTitle(data.title());
        comic.setDescription(data.description());
        updateAuthor(comic, author, category);

        if (data.coverImage() != null && !data.coverImage().isEmpty()) {
            ComicCoverImage(comic, data.coverImage());
        }

        comicRepository.save(comic);
        return new CreateComicRes(
                comic.getId(),
                comic.getTitle(),
                comic.getDescription(),
                comic.getCategory().getName(),
                comic.getAuthor().getName(),
                comic.getAvrRating(),
                comic.getCreatedAt()
        );
    }

    @Override
    public String deleteComic(UUID id) {
        Comic comic = retriveComic(id);
        comicRepository.delete(comic);
        return ResultCode.DELETE_CATEGORY.getMessage();
    }

}
