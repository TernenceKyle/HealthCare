package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

public interface CheckGroupMapper {
    public CheckGroup findById(int id);
    public Page<CheckGroup> findByCondition(String queryString);
    public boolean delete(Integer id);
    public boolean update(CheckGroup Group);
    public boolean add(CheckGroup Group);
    public boolean addBind(@Param("p1")Integer gid,@Param("p2")Integer[] ids);
    public int[] getBindItems(int gid);
    public boolean deleteBind(Integer id);
}
