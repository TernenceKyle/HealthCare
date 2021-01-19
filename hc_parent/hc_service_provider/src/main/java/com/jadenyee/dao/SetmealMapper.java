package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {
    public List<Setmeal> findAll();
    public Setmeal findById(Integer id);
    public Page<Setmeal> findByCondition(String queryString);
    public boolean add(Setmeal setmeal);
    public boolean addCheckGroups(@Param("sid") Integer sid,@Param("cgIds") Integer[] cgIds);
    public boolean delete(Integer id);
    public boolean deleteCheckGroups(Integer sid);
    public boolean update(Setmeal setmeal);
    public Integer[] getCheckGroups(Integer sid);
}
