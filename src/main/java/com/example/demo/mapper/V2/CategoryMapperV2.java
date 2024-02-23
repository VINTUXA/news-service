package com.example.demo.mapper.V2;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import com.example.demo.web.model.CategoryListResponse;
import com.example.demo.web.model.CategoryResponse;
import com.example.demo.web.model.UpsetCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class, NewsMapperV2.class})
public interface CategoryMapperV2 {

    @Mapping(target = "name", source = "categoryName")
    Category requestToCategory(UpsetCategoryRequest request);

    @Mapping(target = "name", source = "request.categoryName")
    public Category requestToCategory(Long categoryId, UpsetCategoryRequest request);

    @Mapping(target = "categoryName", source = "name")
    public CategoryResponse categoryToResponse(Category category);

    default List<CategoryResponse> categoryListToResponseList(List<Category> categories){
        return categories.stream()
                .map(this::categoryToResponse)
                .collect(Collectors.toList());
    }

    default CategoryListResponse categoryListToNewsListResponse(List<Category> categories){
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categoryListToResponseList(categories));

        return response;
    }
}
