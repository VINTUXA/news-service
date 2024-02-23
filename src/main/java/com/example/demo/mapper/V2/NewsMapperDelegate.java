package com.example.demo.mapper.V2;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.service.impl.DatabaseCategoryService;
import com.example.demo.service.impl.DatabaseUserService;
import com.example.demo.web.model.UpsetNewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapperV2{
    @Autowired
    private DatabaseUserService userService;
    @Autowired
    private DatabaseCategoryService categoryService;

    @Override
    public News requestToNews(UpsetNewsRequest request){
        News news = new News();
        news.setCreator(userService.findById(request.getCreatorId()));
        news.setCategory(categoryService.findById(request.getCategoryId()));
        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsetNewsRequest request){
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }

}
