package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.UserOperationLog;

import java.util.List;

public interface LogMapper {
    public List<UserOperationLog> findAll();
    public boolean add(UserOperationLog log);
    public Page<UserOperationLog> findByCondition(String queryString);
}
