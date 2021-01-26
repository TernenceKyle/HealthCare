package com.jadenyee.service;

import com.jadenyee.dao.PermissionMapper;
import com.jadenyee.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionMapper mapper;
    @Override
    public List<Permission> getPermissionList() {
        return mapper.findAll();
    }

    @Override
    public Permission getPermission(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public boolean deletePermission(Integer id) {
        return mapper.delete(id);
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return mapper.update(permission);
    }

    @Override
    public boolean addPermission(Permission permission) {
        return mapper.add(permission);
    }
}
