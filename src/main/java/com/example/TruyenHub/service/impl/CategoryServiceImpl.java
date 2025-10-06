package com.example.TruyenHub.service.impl;

import com.example.TruyenHub.dto.req.CategoryReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.EditCategoryReq;
import com.example.TruyenHub.dto.res.CategoryRes;
import com.example.TruyenHub.exception.DelegationServiceException;
import com.example.TruyenHub.outfras.repo.CategoryRepository;
import com.example.TruyenHub.mapper.CategoryMapper;
import com.example.TruyenHub.model.entity.Category;
import com.example.TruyenHub.model.enums.ResultCode;
import com.example.TruyenHub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryRes createCategory(CommonReq<CategoryReq> req) {
        CategoryReq data = req.getData();

        Category category = categoryRepository.save( categoryMapper.toEntity(data));
            return new CategoryRes(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            );
    }

    @Override
    public CategoryRes editCategory(CommonReq<EditCategoryReq> req) {
        EditCategoryReq data = req.getData();

        Category category = categoryRepository.findById(data.id())
                .orElseThrow(() -> new DelegationServiceException(
                        ResultCode.ID_NOT_FOUND.getCode(),
                        ResultCode.ID_NOT_FOUND.getMessage().formatted(data.id())
                ));

        category.setName(data.name());
        category.setDescription(data.description());
        Category save = categoryRepository.save(category);

        return  new CategoryRes(
                save.getId(),
                save.getName(),
                save.getDescription()
        );
    }
}
