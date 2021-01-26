package com.jadenyee.service;

import com.jadenyee.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    User getUserByUsername(String username);
    boolean deleteUser(Integer id);
    boolean updateUser(User user);
    boolean addUser(User user);
}
