package com.jadenyee.service;

import com.jadenyee.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoleList();
    Role getRoleById(Integer id);
    boolean addRole(Role role);
    boolean deleteRole(Role role);
    boolean updateRole(Role role);
}
