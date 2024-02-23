package com.example.demo.mapper.V2;

import com.example.demo.model.Comment;
import com.example.demo.web.model.CommentListResponse;
import com.example.demo.web.model.CommentResponse;
import com.example.demo.web.model.UpsetCommentRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class) // мапстракт сгенерит доп методы и обернет оригинальный маппер
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapperV2 {
    Comment requestToComment(UpsetCommentRequest request);

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
