package com.jadenyee.service;

import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Order;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public interface OrderService {
    Result orderSetmeal(String plateform, Map orderInfo) throws ParseException;
    Order findOrder(Integer id);
    Map<String,String> getOrderDetail(Integer id);
    Map<String,Integer> getOrderStat();
}
