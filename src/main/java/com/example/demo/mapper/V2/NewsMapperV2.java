package com.example.demo.mapper.V2;

import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import com.example.demo.web.model.NewsListResponse;
import com.example.demo.web.model.NewsResponse;
import com.example.demo.web.model.NewsResponseForOneNews;
import com.example.demo.web.model.UpsetNewsRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

//@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class, UserMapperV2.class, CategoryMapperV2.class})
public interface NewsMapperV2 {

    @Mapping(target = "creator", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "id", ignore = true)
    News requestToNews(UpsetNewsRequest request, User user, Category category);


    public News requestToNews(Long newsId, UpsetNewsRequest request);

    @Mapping(source = "creator.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    default NewsResponse newsToResponse(News news){
        NewsResponse response = new NewsResponse();
        response.setNewsText(news.getNewsText());
        response.setId(news.getId());
        if (news.getCreator() != null){
            response.setAuthorId(news.getCreator().getId());
        }
        if (news.getCategory() != null){
            response.setCategoryId(news.getCategory().getId());
        }
        response.setTitle(news.getTitle());
        response.setNewsText(news.getNewsText());
        response.setCommentCount(news.getComments().size());
        return response;
    }

    @Mapping(source = "creator.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    public NewsResponseForOneNews oneNewsToResponse(News news);

//    }

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
