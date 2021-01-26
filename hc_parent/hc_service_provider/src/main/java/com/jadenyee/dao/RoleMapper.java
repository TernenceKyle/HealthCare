package com.jadenyee.dao;

import com.jadenyee.pojo.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> findAll();
    Role findById(Integer id);
    boolean delete(Integer id);
    boolean update(Role role);
    boolean add(Role role);
    List<Role> findByUserId(Integer id);
}
