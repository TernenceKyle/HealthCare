package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import com.jadenyee.service.AuthorityService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限管理
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Reference
    AuthorityService authorityService;

    //分页查询用户信息
    @RequestMapping("/userList")
    public PageResult userList(@RequestBody QueryPageBean pageBean){
        return authorityService.findPage(pageBean);
    }

    //新建用户
    @RequestMapping("/userAdd")
    public Result userAdd(@RequestBody User user){
        try {
            authorityService.userAdd(user);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_FAIL);
        }
    }

    //查询全部的角色
    @RequestMapping("/findAllRole")
    public Result findRoles(){
        try {
           List<Role> role = authorityService.findAllRole();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,role);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }
    //查询用户拥有的角色
    @RequestMapping("/findRolesByUser")
    public Result findRolesByUser(Integer id){
        try {
            List<Integer> roles = authorityService.findRolesByUser(id);
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roles);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @RequestMapping("/userRoleAdd")
    public Result userRoleAdd (Integer userId,@RequestBody List<Integer> roleId){
        try {
            authorityService.editUserRole(userId,roleId);
            return new Result(true,MessageConstant.ADD_USER_ROLE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_ROLE_FAIL);
        }
    }
}
