package com.example.demo.mapper.V2;

import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.web.model.CommentListResponse;
import com.example.demo.web.model.CommentResponse;
import com.example.demo.web.model.UpsetCommentRequest;
import org.mapstruct.*;

import java.util.List;

//@DecoratedWith(CommentMapperDelegate.class) // мапстракт сгенерит доп методы и обернет оригинальный маппер
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapperV2 {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "news", source = "news")
    @Mapping(target = "id", ignore = true)
    Comment requestToComment(UpsetCommentRequest request, User user, News news);


    Comment requestToComment(Long commentId, UpsetCommentRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "news.id", target = "newsId")
    CommentResponse commentToResponse(Comment comment);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "news.id", target = "newsId")
    List<CommentResponse> commentListToResponseList(List<Comment> orders);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "news.id", target = "newsId")
    default CommentListResponse commentListToCommentListResponse(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponseList(comments));

        return response;
    }
}
