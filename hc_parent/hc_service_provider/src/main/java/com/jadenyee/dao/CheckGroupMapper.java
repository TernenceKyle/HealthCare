package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.CheckGroup;

public interface CheckGroupMapper {
    public CheckGroup findById(int id);
    public Page<CheckGroup> findByCondition(String queryString);
    public boolean delete(Integer id);
    public boolean update(CheckGroup Group);
    public boolean add(CheckGroup Group);
}
