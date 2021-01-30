package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.AuthorityDao;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
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
        authorityDao.userAdd(user);
    }
    //查询所有的角色
    @Override
    public List<Role> findAllRole() {
        List<Role> role = authorityDao.findAllRole();
        return role;
    }
    //查询当前用户拥有的角色
    @Override
    public List<Integer> findRolesByUser(Integer id) {
        List<Integer> roles = authorityDao.findRolesByUser(id);
        return roles;
    }

    //修改用户的角色
    @Override
    public void editUserRole(Integer userId, List<Integer> roleIds) {
        //删除用户原本拥有的角色
        List<Integer> roles = authorityDao.findRolesByUser(userId);
        if (roles != null && roles.size() > 0){
            authorityDao.deleteUserRole(userId);
        }
        //添加用户的新角色
        if (roleIds.size() > 0){
            for (Integer roleId : roleIds) {
                authorityDao.addUserRole(userId, roleId);
            }
        }
    }


}
