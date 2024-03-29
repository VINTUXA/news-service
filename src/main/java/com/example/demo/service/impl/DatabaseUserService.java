package com.example.demo.service.impl;

import com.example.demo.aop.Security;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.DatabaseUserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.web.model.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseUserService implements UserService {
    private final DatabaseUserRepository userRepository;

    @Override
    public List<User> filterBy(UserFilter filter) {
        return userRepository.findAllByUserName(filter.getUserName(), PageRequest.of(
                filter.getPageNumber(), filter.getPageSize()
        )).getContent();
    }

    @Override
    public List<User> findAll(UserFilter filter) {
//        return userRepository.findAll();
        return userRepository.findAll(PageRequest.of(
                filter.getPageNumber(), filter.getPageSize()
        )).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("User with id {0} not found", id)));
    }

    @Override
    public User save(User user) {
        System.out.println(user.getUserName());
        return userRepository.save(user);
    }

    @Override
    @Security
    public User update(User user) {
        User existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);
        return userRepository.save(user);
    }

    @Override
    @Security
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
