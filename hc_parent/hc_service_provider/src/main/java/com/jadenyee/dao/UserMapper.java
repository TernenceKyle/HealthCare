package com.jadenyee.dao;

import com.jadenyee.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    User findByUsername(String username);
    boolean add(User user);
    boolean delete(Integer id);
    boolean update(User user);
}
