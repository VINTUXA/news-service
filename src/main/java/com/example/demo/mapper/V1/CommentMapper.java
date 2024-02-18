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

    private final UserService userService;
    private final NewsService newsService;

    public Comment requestToComment(UpsetCommentRequest request){
        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setAuthor(userService.findById(request.getUserid()));
        comment.setNews(newsService.findById(request.getNewsId()));

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
        commentResponse.setNewsId(newsService.findById(comment.getNews().getId()).getId());

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
