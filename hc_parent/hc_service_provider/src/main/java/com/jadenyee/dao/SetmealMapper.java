package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    public List<Setmeal> findAll();
    /*获取套餐详细内容*/
    public Setmeal findById(Integer id);
    /*普通的仅获取 套餐信息内容*/
    public Setmeal getById(Integer id);

    public Page<Setmeal> findByCondition(String queryString);

    public boolean add(Setmeal setmeal);

    public boolean addCheckGroups(@Param("sid") Integer sid, @Param("cgIds") Integer[] cgIds);

    public boolean delete(Integer id);

    public boolean deleteCheckGroups(Integer sid);

    public boolean update(Setmeal setmeal);

    public Integer[] getCheckGroups(Integer sid);

    public List<Map<String, Object>> getSetmealStatisticByOrder();

    public List<Map<String, Object>> getHotSetmealStat();
}
