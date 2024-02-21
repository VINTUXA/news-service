package com.example.demo.repository.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryNewsRepository implements NewsRepository {
    private CommentRepository commentRepository;
    private final Map<Long, News> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Autowired
    public void setRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<News> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<News> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public News save(News news) {
        Long newsId = currentId.getAndIncrement();
        news.setId(newsId);
        Instant now = Instant.now();
        news.setCreateAt(now);
        news.setUpdateAt(now);

        repository.put(newsId, news);
        return news;
    }

    @Override
    public News update(News news) {
        Long newsId = news.getId();
        Instant now = Instant.now();

        News currentNews = repository.get(newsId);

        if (currentNews == null){
            throw new EntityNotFoundException(MessageFormat.format("News with id {0} not found", newsId));
        }
        BeanUtils.copyNonNullProperties(news, currentNews);

        currentNews.setUpdateAt(now);
        currentNews.setCreateAt(now);
        repository.put(newsId, currentNews);
        return currentNews;
    }

    @Override
    public void deleteById(Long id) {
        News news = repository.get(id);
        if (news == null){
            throw new EntityNotFoundException(MessageFormat.format("News with id {0} not found", id));
        }

        //удаляем все комменты у новости
        commentRepository.deleteByIdIn(news.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }



}
