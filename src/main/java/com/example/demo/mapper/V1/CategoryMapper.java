package com.example.demo.mapper.V1;

import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import com.example.demo.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final CategoryService categoryServiceImpl;
    private final NewsService newsServiceImpl;
    private final NewsMapper newsMapper;

    public Category requestToCategory(UpsetCategoryRequest request){
        Category category = new Category();
        category.setName(request.getCategoryName());

        return category;
    }

    public Category requestToCategory(Long categoryId, UpsetCategoryRequest request){
        Category category = requestToCategory(request);
        category.setId(categoryId);
        return category;
    }

    public CategoryResponse categoryToResponse(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setCategoryName(category.getName());
        categoryResponse.setNewsList(newsMapper.newsListToResponseList(category.getNewsList()));

        return categoryResponse;
    }

    public List<CategoryResponse> categoryListToResponseList(List<Category> categories){
        return categories.stream()
                .map(this::categoryToResponse)
                .collect(Collectors.toList());
    }

    public CategoryListResponse categoryListToNewsListResponse(List<Category> categories){
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToResponseList(categories));

        return response;
    }
}
