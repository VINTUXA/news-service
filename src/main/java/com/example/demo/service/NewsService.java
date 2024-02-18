package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.News;

import java.util.List;

public interface NewsService {

    List<News> findAll();

    News findById(Long id);

    News save(News news);

    News update(News news);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
