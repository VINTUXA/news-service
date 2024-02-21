package com.example.demo.service.impl;

import com.example.demo.aop.Security;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.DatabaseNewsRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.NewsService;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {
    private final DatabaseNewsRepository newsRepository;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("News with id {0} not found", id)));
    }

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    @Security
    public News update(News news) {
        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        return newsRepository.save(news);
    }

    @Override
    @Security
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        newsRepository.deleteAllById(ids);
    }

    @Override
    public Long getCreatorIdByNews(News news) {
        return news.getCreator().getId();
    }
}
