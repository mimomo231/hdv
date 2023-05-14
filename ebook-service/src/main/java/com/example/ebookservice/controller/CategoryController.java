package com.example.ebookservice.controller;

import com.example.ebookservice.payload.response.CategoryResponse;
import com.example.ebookservice.payload.request.CategoryRequest;
import com.example.ebookservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/category", produces = "application/json")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAll() {

        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{cateId}")
    public ResponseEntity<CategoryResponse> getDetail(
            @PathVariable Integer cateId
    ) {
        return ResponseEntity.ok(categoryService.findById(cateId));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> addCategory(
            @RequestBody CategoryRequest request
    ) {

        return ResponseEntity.ok(categoryService.addCategory(request));
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(request));
    }
}
