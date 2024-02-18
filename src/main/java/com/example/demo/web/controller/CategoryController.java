package com.example.demo.web.controller;

import com.example.demo.mapper.V1.CategoryMapper;
import com.example.demo.mapper.V1.NewsMapper;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import com.example.demo.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(){
        return ResponseEntity.ok(categoryMapper.categoryListToNewsListResponse(categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody UpsetCategoryRequest upsetCategoryRequest){
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(upsetCategoryRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                               @RequestBody UpsetCategoryRequest request){
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(id, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
