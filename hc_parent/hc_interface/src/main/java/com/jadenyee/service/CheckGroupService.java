package com.jadenyee.service;

import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    public boolean addCheckGroup(CheckGroup Group,Integer[] ids);
    public boolean deleteCheckGroup(int id) throws Exception;
    public CheckGroup findById(int id);
    public PageResult findByContiditon(QueryPageBean bean);
    public boolean updateCheckGroup(CheckGroup Group,Integer[] ids);
}
