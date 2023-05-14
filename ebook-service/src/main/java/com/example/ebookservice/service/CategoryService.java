package com.example.ebookservice.service;

import com.example.ebookdatamodel.entity.Category;
import com.example.ebookservice.payload.request.CategoryRequest;
import com.example.ebookservice.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAll();

    CategoryResponse findById(Integer cateId);

    CategoryResponse addCategory(CategoryRequest request);

    CategoryResponse updateCategory(CategoryRequest request);

    CategoryResponse mapToCategoryResponse(Category category);
}
