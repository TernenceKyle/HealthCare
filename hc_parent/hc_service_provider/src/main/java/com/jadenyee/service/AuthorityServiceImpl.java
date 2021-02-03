package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.AuthorityDao;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Permission;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import org.apache.dubbo.config.annotation.Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限管理服务
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    AuthorityDao authorityDao;
    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<User> userList = authorityDao.findUserList(queryString);
        return new PageResult(userList.getTotal(),userList.getResult());
    }

    //添加新用户
    @Override
    public void userAdd(User user) {
       // System.out.println(user);
        //用户密码
        String password = user.getPassword();
        //密码加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        //加密
        String newPassword = passwordEncoder.encode(password);
        //将加后的密码写入
        user.setPassword(newPassword);
        //判断用户状态
        if (user.getStation() == null){
            user.setStation("0");
        }
        authorityDao.userAdd(user);
    }
    //查询所有的角色
    @Override
    public List<Role> findAllRole() {
        List<Role> role = authorityDao.findAllRole();
        return role;
    }
    //查询所有的角色
    @Override
    public List<Role> findRoleByQueryString(String queryString) {
        if (queryString == null){
            queryString = "";
        }
        List<Role> role = authorityDao.findRoleByQueryString(queryString);
        return role;
    }
    //查询当前用户拥有的角色
    @Override
    public List<Integer> findRolesByUser(Integer id) {
        List<Integer> roles = authorityDao.findRolesByUser(id);
        return roles;
    }
    //查询当前用户拥有的角色
    @Override
    public void deleteRole(Integer id) {
        authorityDao.deleteRole(id);
    }

    //查询全部权限
    @Override
    public List<Permission> findAllPermission() {
        return authorityDao.findAllPermission();
    }

    //通过角色id查询拥有的权限id
    @Override
    public List<Integer> findPermissionsByRole(Integer id) {
        return authorityDao.findPermissionsByRole(id);
    }

    //根据id查询用户
    @Override
    public User findAllUser(Integer id) {
        return authorityDao.findAllUser(id);
    }

    //编辑用户信息
    @Override
    public void editUser(User user) {

        //用户密码
        String password = user.getPassword();
        if (password != null && password.length() > 0){
            //密码加密
            BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            //加密
            String newPassword = passwordEncoder.encode(password);
            //将加后的密码写入
            user.setPassword(newPassword);
        }else {
            user.setPassword(null);
        }

        //判断用户状态
        if (user.getStation() == null){
            user.setStation("0");
            System.out.println();
        }
        authorityDao.editUser(user);
    }

    //修改用户的角色
    @Override
    public void editUserRole(Integer userId, List<Integer> roleIds) {
        //查询用户原本拥有的角色
        List<Integer> roles = authorityDao.findRolesByUser(userId);
        if (roles != null && roles.size() > 0){
            //删除用户原本拥有的角色
            authorityDao.deleteUserRole(userId);
        }
        //添加用户的新角色
        if (roleIds.size() > 0){
            for (Integer roleId : roleIds) {
                authorityDao.addUserRole(userId, roleId);
            }
        }
    }
    @Override
    public void deleteUser(Integer id) {
        //查询用户原本拥有的角色
        List<Integer> roles = authorityDao.findRolesByUser(id);
        if (roles != null && roles.size() > 0){
            //删除用户原本拥有的角色
            authorityDao.deleteUserRole(id);
        }
        //删除用户
        authorityDao.deleteUser(id);
    }

    //添加角色
    @Override
    public void roleAdd(Role role) {
        authorityDao.roleAdd(role);
    }
    //根据id查询角色
    @Override
    public Role findAllRoleById(Integer id) {
       return authorityDao.findAllRoleById(id);
    }
    //编辑角色
    @Override
    public void editRole(Role role ,Integer id) {
        role.setId(id);
        authorityDao.editRole(role);
    }

    //修改角色的权限
    @Override
    public void editRolePermission(Integer roleId, List<Integer> permissions) {
        //删除角色原本拥有的权限
        List<Integer> oldPer = authorityDao.findPermissionsByRole(roleId);
        if (oldPer != null && oldPer.size() > 0){
            authorityDao.deleteRolePermission(roleId);
        }
        //添加角色的新权限
        if (permissions.size() > 0){
            for (Integer perId : permissions) {
                authorityDao.addRolePermission(roleId, perId);
            }
        }
    }

    //查询权限列表数据
    @Override
    public PageResult findPermissionPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> permissionList = authorityDao.findPermissionList(queryString);
        return new PageResult(permissionList.getTotal(),permissionList.getResult());
    }



    //添加权限
    @Override
    public void PermissionAdd(Permission permission) {
        authorityDao.permissionAdd(permission);
    }

    //根据权限id查询单条权限
    @Override
    public Permission getPermission(Integer id) {
        return authorityDao.getPermission(id);
    }

    //修改权限
    @Override
    public void updatePermission(Permission permission) {
        authorityDao.updatePermission(permission);
    }

    //删除权限
    @Override
    public void deletePermission(Integer id) {
        //删除角色_权限表中的对应数据
        authorityDao.deletePermissionOne(id);
        //删除权限数据
        authorityDao.deletePermissionTwo(id);
    }




}
