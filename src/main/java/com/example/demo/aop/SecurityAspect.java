package com.example.demo.aop;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Aspect
@Component
@Slf4j
public class SecurityAspect {

    private final NewsService newsService;
    private final CommentService commentService;

    @Autowired
    public SecurityAspect(NewsService newsService, CommentService commentService) {
        this.newsService = newsService;
        this.commentService = commentService;
    }


    @Before("@annotation(Security) && args(news)")
    public void checkUser(News news){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String userIdParam = request.getParameter("userId");
        Long userIdFromRequest = Long.valueOf(userIdParam);
        System.out.println("user id is: " + userIdFromRequest);

        long creatorId = newsService.getCreatorIdByNews(news);
        if (creatorId != userIdFromRequest){
            throw new SecurityException("You are not authorized to edit or delete this news.");
        }
    }
    @Before("@annotation(Security) && args(id)")
    public void checkUser(Long id){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String userIdParam = request.getParameter("userId");
        Long userIdFromRequest = Long.valueOf(userIdParam);
        System.out.println("user id is: " + userIdFromRequest);

        long creatorId = newsService.findById(id).getCreator().getId();
        if (creatorId != userIdFromRequest){
            throw new SecurityException("You are not authorized to edit or delete this news.");
        }
    }

    @Before("@annotation(Security) && args(comment)")
    public void checkUser(Comment comment){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String userIdParam = request.getParameter("userId");
        Long userIdFromRequest = Long.valueOf(userIdParam);
        System.out.println("user id is: " + userIdFromRequest);

        long creatorId = commentService.findById(comment.getId()).getAuthor().getId();
        if (creatorId != userIdFromRequest){
            throw new SecurityException("You are not authorized to edit or delete this comment.");
        }
    }
}
