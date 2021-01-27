package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Order;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    public List<Order> findAll();
    public Order findById(Integer id);
    public boolean delete(Integer id);
    public boolean add(Order order);
    public boolean update(Order order);
//    public Page<Order> findByCondition(String queryString);
    public Order findByMemberAndDate(@Param("mid") Integer mid,@Param("date") Date date);
    public Map getOrderDetail(Integer id);
    public Integer countByDate(String date);
    public Integer countByPeriod(@Param("start") String start,@Param("end") String end);
    public Integer countFinishedByDate(String date);
    public Integer countFinishedByPeriod(@Param("start") String start,@Param("end") String end);
}
