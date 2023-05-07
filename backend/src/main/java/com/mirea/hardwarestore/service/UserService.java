package com.mirea.hardwarestore.service;

import com.mirea.hardwarestore.model.user.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findById(Long id);

    User findByEmail(String email);

    void delete(Long id);
}
