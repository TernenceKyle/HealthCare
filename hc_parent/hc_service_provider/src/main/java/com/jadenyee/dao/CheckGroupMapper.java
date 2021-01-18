package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupMapper {
    public List<CheckGroup> findAll();
    public CheckGroup findById(int id);
    public Page<CheckGroup> findByCondition(String queryString);
    public int[] getBindItems(int gid);
    public boolean delete(Integer id);
    public boolean deleteBind(Integer id);
    public boolean add(CheckGroup Group);
    public boolean addBind(@Param("p1")Integer gid,@Param("p2")Integer[] ids);
    public boolean update(CheckGroup Group);
}
