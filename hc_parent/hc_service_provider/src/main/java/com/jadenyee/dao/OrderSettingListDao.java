package com.jadenyee.dao;

import com.github.pagehelper.Page;
import com.jadenyee.pojo.Address;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.OrderSetting;
import com.jadenyee.pojo.Setmeal;


import java.util.List;
import java.util.Map;

public interface OrderSettingListDao {


    Page<Map<String,Object>> findByCondition(String queryString);

    List<Setmeal> findSetmeal();

    void add(Map<String, Object> map);

    void addorder(Map<String, Object> map);

    void update(Order order);

    void delete(Integer id);

    List<Address> addressAll();
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    public List<Order> findByConditionMove(Order order);
}
