package com.jadenyee.dao;

import com.jadenyee.pojo.Permission;

import java.util.List;

public interface PermissionMapper {
    List<Permission> findAll();
    Permission findById(Integer id);
    List<Permission> findByRoleId(Integer id);
    boolean delete(Integer id);
    boolean add(Permission permission);
    boolean update(Permission permission);
}
