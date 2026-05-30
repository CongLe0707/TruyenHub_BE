package com.example.TruyenHub.service;

import com.example.TruyenHub.dto.req.CategoryReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.EditCategoryReq;
import com.example.TruyenHub.dto.res.CategoryRes;

public interface CategoryService {
    CategoryRes createCategory (CommonReq<CategoryReq> req);

    CategoryRes editCategory (CommonReq<EditCategoryReq> req);

    void deleteCategory (java.util.UUID id);

    java.util.List<CategoryRes> listCategory();
}
