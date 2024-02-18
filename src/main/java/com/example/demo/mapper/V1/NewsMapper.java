package com.example.demo.mapper.V1;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.example.demo.web.model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper {
    private final CategoryService categoryService;
    private final CommentMapper commentMapper;

    public News requestToNews(UpsetNewsRequest request){
        News news = new News();
        news.setNewsText(request.getNewsText());
        news.setCategory(categoryService.findById(request.getCategoryId()));

        return news;
    }

    public News requestToNews(Long newsId, UpsetNewsRequest request){
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }

    public NewsResponse newsToResponse(News news){
        NewsResponse newsResponse = new NewsResponse();

        newsResponse.setId(news.getId());
        newsResponse.setNewsText(news.getNewsText());
        newsResponse.setComments(commentMapper.commentListToResponseList(news.getComments()));

        return newsResponse;
    }

    public List<NewsResponse> newsListToResponseList(List<News> newsList){
        return newsList.stream()
                .map(this::newsToResponse)
                .collect(Collectors.toList());
    }

    public NewsListResponse newsListToNewsListResponse(List<News> newsList){
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToResponseList(newsList));

        return response;
    }
}
