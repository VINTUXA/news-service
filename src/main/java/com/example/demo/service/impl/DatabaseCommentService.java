package com.example.demo.service.impl;

import com.example.demo.aop.Security;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.DatabaseCommentRepository;
import com.example.demo.service.CommentService;
import com.example.demo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentService implements CommentService {

    private final DatabaseCommentRepository commentRepository;
    private final DatabaseUserService userService;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Comment with id {0} not found", id)));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
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