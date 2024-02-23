package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseCommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByNewsId(Long newsId, Pageable pageable);
}
