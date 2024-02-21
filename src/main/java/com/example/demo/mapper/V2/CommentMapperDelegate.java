package com.example.demo.mapper.V2;

import com.example.demo.model.Comment;
import com.example.demo.repository.DatabaseNewsRepository;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.DatabaseUserService;
import com.example.demo.web.model.UpsetCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapperV2{

    @Autowired
    private UserService databaseUserService;

    @Autowired
    private NewsService databaseNewsService;

    @Override
    public Comment requestToComment(UpsetCommentRequest request){
        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setUser(databaseUserService.findById(request.getUserId()));
        comment.setNews(databaseNewsService.findById(request.getNewsId()));

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsetCommentRequest request){
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}
