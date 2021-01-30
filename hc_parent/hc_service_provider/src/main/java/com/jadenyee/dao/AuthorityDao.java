package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityDao {
    public Page<User> findUserList(String queryString);
    public void userAdd(User user);
    public List<Role> findAllRole();
    public List<Integer> findRolesByUser(Integer id);
    public void deleteUserRole(Integer userId);
    public void addUserRole(@Param("userId") Integer userId,@Param("roleId")Integer roleId);
}
