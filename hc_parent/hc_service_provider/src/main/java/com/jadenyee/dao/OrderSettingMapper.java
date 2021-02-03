package com.jadenyee.dao;

import com.jadenyee.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {
    OrderSetting findById(Integer id);
    OrderSetting findByDate(Date date);
    List<OrderSetting> findByRange(@Param("start") Date start, @Param("end") Date end);
    List<OrderSetting> findALL();
    boolean delete(Integer id);
    boolean update(OrderSetting ordersetting);
    boolean add(OrderSetting orderSetting);
    boolean batchAdd(List<OrderSetting> lists);
}
