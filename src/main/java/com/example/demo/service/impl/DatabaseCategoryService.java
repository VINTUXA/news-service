package com.example.demo.service.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.DatabaseCategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.CategoryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCategoryService implements CategoryService {
    private final DatabaseCategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(CategoryFilter filter) {
        return categoryRepository.findAll(PageRequest.of(
                filter.getPageNumber(), filter.getPageSize()
        )).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Category with id {0} not found", id)));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category existedCategory = findById(category.getId());
        BeanUtils.copyNonNullProperties(category, existedCategory);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        categoryRepository.deleteAllById(ids);
    }
}
