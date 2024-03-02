package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.web.model.NewsFilter;
import com.example.demo.web.model.UpsetNewsRequest;

import java.util.List;

public interface NewsService {

    List<News> filterBy(NewsFilter newsFilter);

    List<News> findAll(NewsFilter newsFilter);

    News findById(Long id);

    News save(UpsetNewsRequest upsetNewsRequest);

    News update(News news);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
    Long getCreatorIdByNews(News news);
}
