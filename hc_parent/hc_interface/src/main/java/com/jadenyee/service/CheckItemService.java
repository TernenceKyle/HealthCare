package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public boolean addCheckItem(CheckItem item);
    public boolean deleteCheckItem(int id) throws Exception;
    public CheckItem findById(int id);
    public PageResult findByItem(QueryPageBean bean);
    public boolean updateCheckItem(CheckItem item);
    public PageResult findAll();
}
