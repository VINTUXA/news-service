package com.example.demo.repository.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.Comment;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCommentRepository implements CommentRepository {
    private UserRepository userRepository;

    private final Map<Long, Comment> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);



    @Override
    public List<Comment> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Comment save(Comment comment) {
        Long commentId = currentId.getAndIncrement();
        Long clientId = comment.getAuthor().getId();
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        comment.setAuthor(user);
        comment.setId(commentId);
        Instant now = Instant.now();
        comment.setCreateAt(now);
        comment.setUpdateAt(now);

        repository.put(commentId, comment);
        user.addComment(comment);
        userRepository.update(user);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        Long commentId = comment.getId();
        Instant now = Instant.now();

        Comment currentComment = repository.get(commentId);

        if (currentComment == null){
            throw new EntityNotFoundException(MessageFormat.format("Comment with id {0} not found", commentId));
        }
        BeanUtils.copyNonNullProperties(comment, currentComment);

        currentComment.setUpdateAt(now);
        currentComment.setCreateAt(now);
        repository.put(commentId, currentComment);
        return currentComment;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(repository::remove);
    }
    @Autowired
    public void setClientRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
