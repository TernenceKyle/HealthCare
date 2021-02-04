package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Permission;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityDao {
    public Page<User> findUserList(String queryString);
    public void userAdd(User user);
    public List<Role> findAllRole();
    public Role findAllRoleById(Integer id);
    public List<Role> findRoleByQueryString(String queryString);
    public List<Integer> findRolesByUser(Integer id);
    public List<Permission> findAllPermission();
    public List<Integer> findPermissionsByRole(Integer id);
    public void deleteUserRole(Integer userId);
    public void addUserRole(@Param("userId") Integer userId,@Param("roleId")Integer roleId);
    public void deleteRolePermission(Integer roleId);
    public void addRolePermission(@Param("roleId") Integer roleId,@Param("perId")Integer perId);
    public Page<Permission> findPermissionList(String queryString);
    public void permissionAdd(Permission permission);
    public Permission getPermission(Integer id);
    public void updatePermission(Permission permission);
    public void deletePermissionOne(Integer id);
    public void deletePermissionTwo(Integer id);
    public User findAllUser(Integer id);
    public void editUser(User user);
    public void deleteUser(Integer id);
    public void roleAdd(Role role);
    public void deleteRole(Integer id);
    public void editRole(Role role);
}
