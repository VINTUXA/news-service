package com.example.demo.repository.impl;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.Comment;
import com.example.demo.utils.BeanUtils;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryUserRepository implements UserRepository {
    private CommentRepository commentRepository;
    private final Map<Long, User> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);
    @Override
    public List<User> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public User save(User user) {
        Long clientId = currentId.getAndIncrement();
        user.setId(clientId);
        repository.put(clientId, user);
        return user;
    }

    @Override
    public User update(User user) {
        Long clientId = user.getId();
        User currentUser = repository.get(clientId);
        if(currentUser == null){
            throw new EntityNotFoundException(MessageFormat.format("User with id {0} not found", clientId));
        }

        BeanUtils.copyNonNullProperties(user, currentUser);
        currentUser.setId(clientId);
        repository.put(clientId, currentUser);
        return currentUser;
    }

    @Override
    public void deleteById(Long id) {
        User user = repository.get(id);
        if (user == null){
            throw new EntityNotFoundException(MessageFormat.format("User with id {0} not found", id));
        }

        //удаляем все заказы клиента
        commentRepository.deleteByIdIn(user.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        repository.remove(id);

    }

    @Autowired
    private void setCommentRepository(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
}
