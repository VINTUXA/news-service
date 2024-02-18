package com.example.demo.service;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentService {
//    List<Comment> filterBy(OrderFilter orderFilter);

    List<Comment> findAll();

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);
}
