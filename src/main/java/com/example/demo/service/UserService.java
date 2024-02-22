package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.web.model.UserFilter;

import java.util.List;

public interface UserService {

    List<User> filterBy(UserFilter filter);

    List<User> findAll(UserFilter filter);

    User findById(Long id);

    User save(User user);

    User update(User user);

    void deleteById(Long id);

}
