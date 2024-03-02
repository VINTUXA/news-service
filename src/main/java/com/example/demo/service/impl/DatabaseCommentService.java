package com.example.demo.service.impl;

import com.example.demo.aop.Security;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.mapper.V2.CommentMapperV2;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.DatabaseCommentRepository;
import com.example.demo.service.CommentService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.CommentFilter;
import com.example.demo.web.model.UpsetCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository commentRepository;
    private final DatabaseUserService userService;
    private final DatabaseNewsService newsService;
    private final CommentMapperV2 commentMapper;

//    @Override
//    public List<Comment> filterBy(CommentFilter filter) {
//        if(filter.getNewsId() == null){
//            throw new RuntimeException("User id should be declared in request params!");
//        }
//        return commentRepository.findAllByNewsId(filter.getNewsId(), PageRequest.of(
//                filter.getPageNumber(), filter.getPageSize()
//        )).getContent();
//    }

    @Override
    public List<Comment> findAll(CommentFilter filter) {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Comment with id {0} not found", id)));
    }

//    @Override
//    public Comment save(Comment comment) {
//        return commentRepository.save(comment);
//    }
    @Override
    public Comment save(UpsetCommentRequest upsetCommentRequest) {
        User user = userService.findById(upsetCommentRequest.getUserId());
        News news = newsService.findById(upsetCommentRequest.getNewsId());
        if (user != null && news != null){
            Comment comment = commentMapper.requestToComment(upsetCommentRequest);
            comment.setUser(user);
            comment.setNews(news);
             return commentRepository.save(comment);
        } else {
            throw new EntityNotFoundException(MessageFormat.format("User {0} or news {0} doesn't exist", upsetCommentRequest.getUserId(),upsetCommentRequest.getNewsId()));
        }
//        return commentRepository.save(commentMapper.requestToComment());
    }

    @Override
    @Security
    public Comment update(Comment comment) {
        Comment existedComment = findById(comment.getId());
        User user = userService.findById(comment.getUser().getId());
        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        return commentRepository.save(comment);
    }

    @Override
    @Security
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        commentRepository.deleteAllById(ids);
    }
}
