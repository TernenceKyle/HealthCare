package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Permission;
import com.jadenyee.pojo.Role;
import com.jadenyee.pojo.User;
import com.jadenyee.service.AuthorityService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 用户管理
     */

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
    //根据id查询角色信息
    @RequestMapping("/findAllUser")
    public Result findAllUser(Integer id){
        try {
                User user = authorityService.findAllUser(id);
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS,user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
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
    //编辑用户信息
    @RequestMapping("/editUser")
    public Result editUser (Integer userId,@RequestBody User user){
        try {
            user.setId(userId);
            authorityService.editUser(user);
            return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }
    }

    //修改用户的角色
    @RequestMapping("/userRoleAdd")
    public Result userRoleAdd (Integer userId,@RequestBody List<Integer> roleId){
        try {
            authorityService.editUserRole(userId,roleId);
            return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_ROLE_FAIL);
        }
    }
    //删除用户信息
    @RequestMapping("/deleteUser")
    public Result deleteUser (Integer id){
        try {
            authorityService.deleteUser(id);
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 角色管理
     */
    //查询角色列表
    @RequestMapping("/roleList")
    public Result roleList (){
        try {
            List<Role> allRole = authorityService.findAllRole();
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,allRole);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }
    //根据条件查询角色
    @RequestMapping("/roleListByQueryString")
    public Result roleListByQueryString (String queryString){

        try {
            queryString = new String(queryString.getBytes("ISO-8859-1"),"utf-8");
            List<Role> allRole = authorityService.findRoleByQueryString(queryString);
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,allRole);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    //添加角色
    @RequestMapping("/roleAdd")
    public Result roleAdd(@RequestBody Role role){
        try {
            authorityService.roleAdd(role);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ROLE_FAIL);
        }
    }
    //根据id查询角色
    @RequestMapping("/findAllRoleById")
    public Result findAllRoleById(Integer id){
        try {
            Role role = authorityService.findAllRoleById(id);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,role);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    //查询全部的权限
    @RequestMapping("/findAllPermission")
    public Result findAllPermission(){
        try {
            List<Permission> permission = authorityService.findAllPermission();
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,permission);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }
    //通过角色id查询拥有的权限id
    @RequestMapping("/findPermissionsByRole")
    public Result findPermissionsByRole(Integer id){
        try {
            List<Integer> permissions = authorityService.findPermissionsByRole(id);
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,permissions);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }
    //编辑角色信息
    @RequestMapping("/editRole")
    public Result editRole (Integer roleId,@RequestBody Role role){
        try {
            authorityService.editRole(role,roleId);
            return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }
    }
    //修改角色的权限
    @RequestMapping("/rolePermissionAdd")
    public Result rolePermissionAdd (Integer roleId,@RequestBody List<Integer> permissionIds){
        try {
            authorityService.editRolePermission(roleId,permissionIds);
            return new Result(true,MessageConstant.ADD_ROLE_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ROLE_PERMISSION_FAIL);
        }
    }

    //删除角色
    @RequestMapping("/deleteRole")
    public Result deleteRole (Integer id){
        try {
            authorityService.deleteRole(id);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    /**
     * 权限管理
     */
    //分页查询权限信息
    @RequestMapping("/permissionList")
    public PageResult permissionList(@RequestBody QueryPageBean pageBean){
        return authorityService.findPermissionPage(pageBean);
    }
    //新建权限
    @RequestMapping("/PermissionAdd")
    public Result PermissionAdd(@RequestBody Permission permission){
        try {
            authorityService.PermissionAdd(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }
    }
    //根据id查询权限
    @RequestMapping("/getPermission")
    public Permission getPermission(Integer id){
        return authorityService.getPermission(id);
    }
    //根据修改权限信息
    @RequestMapping("/updatePermission")
    public Result updatePermission(@RequestBody Permission permission){
        try {
            authorityService.updatePermission(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }
    //根据id删除权限信息
    @RequestMapping("/deletePermission")
    public Result deletePermission(Integer id){
        try {
            authorityService.deletePermission(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

}
