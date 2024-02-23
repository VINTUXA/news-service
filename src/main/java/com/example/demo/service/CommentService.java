package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.web.model.CommentFilter;

import java.util.List;

public interface CommentService {
    List<Comment> filterBy(CommentFilter filter);

    List<Comment> findAll(CommentFilter filter);

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);
}
