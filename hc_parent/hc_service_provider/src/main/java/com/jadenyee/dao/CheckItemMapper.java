package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {
    //    public List<CheckItem> findAll();
    public CheckItem findById(int id);
    public boolean add(CheckItem item);
    public Page<CheckItem> findByCondition(String queryString);
    public boolean delete(Integer id);
    public boolean update(CheckItem item);
    public int getGroupBindCount(int id);
    public boolean addBind(@Param("gid") int gid,@Param("list") List<Integer> itemlist);
    public boolean deleteBind(int id);
}
