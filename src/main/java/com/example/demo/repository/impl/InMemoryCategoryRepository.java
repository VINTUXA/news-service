package com.example.demo.repository.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryCategoryRepository implements CategoryRepository {
    private NewsRepository newsRepository;
    private final Map<Long, Category> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Category save(Category category) {
        Long categoryId = currentId.getAndIncrement();
        category.setId(categoryId);
        Instant now = Instant.now();
        category.setCreateAt(now);
        category.setUpdateAt(now);

        repository.put(categoryId, category);
        return category;
    }

    @Override
    public Category update(Category category) {
        Long categoryId = category.getId();
        Instant now = Instant.now();

        Category currentCategory = repository.get(categoryId);

        if (currentCategory == null){
            throw new EntityNotFoundException(MessageFormat.format("Category with id {0} not found", categoryId));
        }
        BeanUtils.copyNonNullProperties(category, currentCategory);

        currentCategory.setUpdateAt(now);
        currentCategory.setCreateAt(now);
        repository.put(categoryId, currentCategory);
        return currentCategory;
    }

    @Override
    public void deleteById(Long id) {
        Category category = repository.get(id);
        if (category == null){
            throw new EntityNotFoundException(MessageFormat.format("Category with id {0} not found", id));
        }

        //удаляем все комменты у новости
        newsRepository.deleteByIdIn(category.getNewsList().stream().map(News::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }
}
