package com.example.demo.web.controller.V2;

import com.example.demo.mapper.V2.CategoryMapperV2;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/category")
@RequiredArgsConstructor
public class CategoriesControllerV2 {

    private final CategoryMapperV2 categoryMapper;
    private final CategoryService databaseCategoryService;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(CategoryFilter filter){
        return ResponseEntity.ok(categoryMapper.categoryListToNewsListResponse(databaseCategoryService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(databaseCategoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody UpsetCategoryRequest upsetCategoryRequest){
        Category newCategory = databaseCategoryService.save(categoryMapper.requestToCategory(upsetCategoryRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @RequestBody UpsetCategoryRequest request){
        Category updatedCategory = databaseCategoryService.update(categoryMapper.requestToCategory(id, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        databaseCategoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
