package com.example.demo.mapper.V2;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.service.impl.DatabaseCategoryService;
import com.example.demo.service.impl.DatabaseUserService;
import com.example.demo.web.model.NewsResponse;
import com.example.demo.web.model.NewsResponseForOneNews;
import com.example.demo.web.model.UpsetNewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public abstract class NewsMapperDelegate implements NewsMapperV2{
    @Autowired
    private DatabaseUserService userService;
    @Autowired
    private DatabaseCategoryService categoryService;

    @Autowired
    private CommentMapperV2 commentMapper;

    @Override
    public NewsResponse newsToResponse(News news){
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

   public NewsResponseForOneNews oneNewsToResponse(News news){
            NewsResponseForOneNews response = new NewsResponseForOneNews();
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
            response.setComments(news.getComments().stream()
//                .map(this::newsToResponse)
                .map(n -> commentMapper.commentToResponse(n))
                .collect(Collectors.toList()));
            return response;
    }

//    @Override
//    public News requestToNews(UpsetNewsRequest request){
//        News news = new News();
//        news.setCreator(userService.findById(request.getCreatorId()));
//        news.setTitle(request.getTitle());
//        news.setNewsText(request.getNewsText());
//        news.setCategory(categoryService.findById(request.getCategoryId()));
//        return news;
//    }
//
//    @Override
//    public News requestToNews(Long newsId, UpsetNewsRequest request){
//        News news = requestToNews(request);
//        news.setId(newsId);
//        return news;
//    }

}
