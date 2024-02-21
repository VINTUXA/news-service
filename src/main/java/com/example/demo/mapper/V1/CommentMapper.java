package com.example.demo.mapper.V1;

import com.example.demo.model.Comment;
import com.example.demo.service.NewsService;
import com.example.demo.service.UserService;
import com.example.demo.web.model.CommentListResponse;
import com.example.demo.web.model.CommentResponse;
import com.example.demo.web.model.UpsetCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userServiceImpl;
    private final NewsService newsServiceImpl;

    public Comment requestToComment(UpsetCommentRequest request){
        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setUser(userServiceImpl.findById(request.getUserId()));
        comment.setNews(newsServiceImpl.findById(request.getNewsId()));

        return comment;
    }

    public Comment requestToComment(Long commentId, UpsetCommentRequest request){
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }

    public CommentResponse commentToResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setCommentText(comment.getCommentText());
        commentResponse.setNewsId(newsServiceImpl.findById(comment.getNews().getId()).getId());

        return commentResponse;
    }

    public List<CommentResponse> commentListToResponseList(List<Comment> comments){
        return comments.stream()
                .map(this::commentToResponse)
                .collect(Collectors.toList());
    }

    public CommentListResponse commentListToOrderListResponse(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponseList(comments));

        return response;
    }
}
