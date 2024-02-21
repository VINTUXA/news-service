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

    @Mapping(source = "commentId", target = "id") // указание мапиинга между свойствами
    Comment requestToComment(Long commentId, UpsetCommentRequest request);

    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToResponseList(List<Comment> orders);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments){
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToResponseList(comments));

        return response;
    }
}
