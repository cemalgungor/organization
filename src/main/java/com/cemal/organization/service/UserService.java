package com.cemal.organization.service;

import java.util.List;

import com.cemal.organization.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(Long id);
    User findOne(String username);

    User findById(Long id);
}