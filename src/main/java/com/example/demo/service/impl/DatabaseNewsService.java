package com.example.demo.service.impl;

import com.example.demo.aop.Security;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mapper.V2.NewsMapperV2;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.DatabaseNewsRepository;
import com.example.demo.repository.NewsSpecification;
import com.example.demo.service.CategoryService;
import com.example.demo.service.NewsService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.NewsFilter;
import com.example.demo.web.model.UpsetNewsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseNewsService implements NewsService {
    private final DatabaseNewsRepository newsRepository;
    private final DatabaseUserService userService;
    private final DatabaseCategoryService categoryService;
    private final NewsMapperV2 newsMapper;

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

//    @Override
//    public List<News> findAll() {
//        return newsRepository.findAll();
//    }
    @Override
    public List<News> findAll(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(), filter.getPageSize()
                )).getContent();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("News with id {0} not found", id)));
    }

//    @Override
//    public News save(News news) {
//        return newsRepository.save(news);
//    }
    @Override
    public News save(UpsetNewsRequest upsetNewsRequest) {
        User user = userService.findById(upsetNewsRequest.getCreatorId());
        Category category = categoryService.findById(upsetNewsRequest.getCategoryId());
        if (user != null && category != null){
            News news = newsMapper.requestToNews(upsetNewsRequest);
            news.setCreator(user);
            news.setCategory(category);
            return newsRepository.save(news);
        } else {
            throw new EntityNotFoundException(MessageFormat.format("User {0} or category {0} doesn't exist", upsetNewsRequest.getCreatorId(), upsetNewsRequest.getCategoryId()));
        }
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
