package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public boolean addCheckItem(CheckItem item);
    public boolean deleteCheckItem(int id);
    public List<CheckItem> findByPage(int page,int size);
    public CheckItem findById(int id);
    public PageResult findByItem(QueryPageBean bean);
    public boolean updateCheckItem(CheckItem item);
}
