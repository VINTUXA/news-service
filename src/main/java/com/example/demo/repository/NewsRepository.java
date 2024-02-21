package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsRepository {
    List<News> findAll();

    Optional<News> findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);

}
