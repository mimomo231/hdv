package com.example.ebookservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.ebookdatamodel.entity.Category;
import com.example.ebookservice.payload.request.CategoryRequest;
import com.example.ebookservice.payload.response.CategoryResponse;
import com.example.ebookservice.repository.CategoryRepository;
import com.example.ebookservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepo.findAll().stream()
                .map(this::mapToCategoryResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Integer cateId) {

        Category category = categoryRepo.findById(cateId).orElseThrow(
                () -> new NotFoundException(
                        String.format("findById error: Not found Category with id: %s", cateId)
                )
        );
        return mapToCategoryResponse(category);
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {

        Category category = categoryRepo.findByName(request.getName());
        if(Objects.isNull(category)){
            Category c = categoryRepo.save(Category.builder()
                            .name(request.getName())
                            .description(request.getDescription())
                    .build());
            return mapToCategoryResponse(c);
        }
        return null;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {

        Category existCategory = categoryRepo.findByName(request.getName());
        if(Objects.isNull(existCategory)) {
            Category category = categoryRepo.save(Category.builder()
                    .id(request.getId())
                    .name(request.getName())
                    .description(request.getDescription())
                    .build());
            return mapToCategoryResponse(category);
        }
        return null;
    }

    @Override
    public CategoryResponse mapToCategoryResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
