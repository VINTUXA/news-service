package com.example.demo.mapper.V2;

import com.example.demo.model.News;
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

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapperV2.class, UserMapperV2.class, CategoryMapperV2.class})
public interface NewsMapperV2 {

    public News requestToNews(UpsetNewsRequest request);

//    @Mapping(source = "creatorId", target = "id")
    public News requestToNews(Long newsId, UpsetNewsRequest request);

    @Mapping(source = "creator.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    public NewsResponse newsToResponse(News news);

    @Mapping(source = "creator.id", target = "authorId")
    @Mapping(source = "category.id", target = "categoryId")
    public NewsResponseForOneNews oneNewsToResponse(News news);
//            NewsResponseForOneNews response = new NewsResponseForOneNews();
//            response.setNewsText(news.getNewsText());
//            response.setId(news.getId());
//            if (news.getCreator() != null){
//                response.setAuthorId(news.getCreator().getId());
//            }
//            if (news.getCategory() != null){
//                response.setCategoryId(news.getCategory().getId());
//            }
//            response.setTitle(news.getTitle());
//            response.setNewsText(news.getNewsText());
//            response.setCommentCount(news.getComments().size());
//            response.setComments(news.getComments().stream()
////                .map(this::newsToResponse)
//                .map(n -> newsToResponse(n))
//                .collect(Collectors.toList()));
//            return response;
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
