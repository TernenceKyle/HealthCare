package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;

import java.util.List;

public interface CheckItemMapper {
    public boolean add(CheckItem item);
    public List<CheckItem> findAll();
    public CheckItem findById();
    public Page<CheckItem> findByCondition(String queryString);
    public boolean delete(Integer id);
    public boolean update(CheckItem item);
    public int getTotal();
}
