package com.example.TruyenHub.controller;

import com.example.TruyenHub.dto.req.CategoryReq;
import com.example.TruyenHub.dto.req.CommonReq;
import com.example.TruyenHub.dto.req.EditCategoryReq;
import com.example.TruyenHub.dto.res.CommonRes;
import com.example.TruyenHub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<CommonRes> createCategory(@RequestBody CommonReq<CategoryReq> req) {

        return ApiHandler.handle(req,categoryService::createCategory);
    }

    @PostMapping("/edit")
    public ResponseEntity<CommonRes> editCategory (@RequestBody CommonReq<EditCategoryReq> req) {

        return  ApiHandler.handle(req,categoryService::editCategory);
    }

    @GetMapping("/list")
    public ResponseEntity<CommonRes> listCategory() {
        return ApiHandler.handle(null, req -> categoryService.listCategory());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonRes> deleteCategory(@PathVariable java.util.UUID id) {
        return ApiHandler.handle(id, categoryService::deleteCategory);
    }


}
