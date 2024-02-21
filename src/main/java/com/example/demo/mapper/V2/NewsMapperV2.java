package com.example.demo.mapper.V2;

import com.example.demo.mapper.V1.CommentMapper;
import com.example.demo.model.News;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import com.example.demo.web.model.NewsListResponse;
import com.example.demo.web.model.NewsResponse;
import com.example.demo.web.model.UpsetNewsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class, UserMapperV2.class, CategoryMapperV2.class})
public interface NewsMapperV2 {

    public News requestToNews(UpsetNewsRequest request);

    public News requestToNews(Long newsId, UpsetNewsRequest request);

    public NewsResponse newsToResponse(News news);

    default List<NewsResponse> newsListToResponseList(List<News> newsList){
        return newsList.stream()
                .map(this::newsToResponse)
                .collect(Collectors.toList());
    }

    default NewsListResponse newsListToNewsListResponse(List<News> newsList){
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToResponseList(newsList));

        return response;
    }
}
