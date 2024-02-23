package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.web.model.CategoryFilter;

import java.util.List;

public interface CategoryService {

    List<Category> findAll(CategoryFilter filter);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
