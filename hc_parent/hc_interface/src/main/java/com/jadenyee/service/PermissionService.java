package com.jadenyee.service;

import com.jadenyee.pojo.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getPermissionList();
    Permission getPermission(Integer id);
    boolean deletePermission(Integer id);
    boolean updatePermission(Permission permission);
    boolean addPermission(Permission permission);
}
