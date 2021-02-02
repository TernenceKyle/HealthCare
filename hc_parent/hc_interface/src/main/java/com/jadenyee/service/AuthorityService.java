package com.jadenyee.service;

import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Permission;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;

import java.util.List;

/**
 * 权限管理服务接口
 */
public interface AuthorityService {
    public PageResult findPage(QueryPageBean queryPageBean);
    public void userAdd(User user);
    public List<Role> findAllRole();
    public List<Integer> findRolesByUser(Integer id);
    public List<Permission> findAllPermission();
    public List<Integer> findPermissionsByRole(Integer id);
    public void editUserRole(Integer userId,List<Integer> roleId);
    public void editRolePermission(Integer roleId,List<Integer> permissions);
    public PageResult findPermissionPage(QueryPageBean queryPageBean);
    public void PermissionAdd(Permission permission);
    public Permission getPermission(Integer id);
    public void updatePermission(Permission permission);
    public void deletePermission(Integer id);
    public User findAllUser(Integer id);
    public void editUser(User user);
    public void deleteUser(Integer id);
}
