package com.jadenyee.service;

import com.jadenyee.entity.PageResult;
import com.jadenyee.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingService {
    public void importOrderSettings(List<OrderSetting> lists);
    public List<OrderSetting> getOrdersettings(Date start,Date end);
    public boolean setOrder(OrderSetting orderSetting);
    public OrderSetting getOrderSettingByDate(Date date);
}
