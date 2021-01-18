package com.jadenyee.service;

import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckGroup;
import com.jadenyee.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    public PageResult getSetmealList(QueryPageBean bean);
    public boolean deleteSetmeal(Integer id);
    public boolean updateSetmeal(Setmeal setmeal, Integer[] groupIds);
    public boolean addSetmeal(Setmeal setmeal,Integer[] groupIds);
    public Setmeal getSetmealById(Integer id);
    public Integer[] getGroups(Integer sid);
    public List<Setmeal> getAll();

}
