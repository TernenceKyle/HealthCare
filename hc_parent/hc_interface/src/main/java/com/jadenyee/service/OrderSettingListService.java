package com.jadenyee.service;



import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Address;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface OrderSettingListService {


    PageResult findPage(QueryPageBean queryPageBean) throws Exception;

    List<Setmeal> findSetmeal();

    Result add(Map<String, Object> map, Integer setmealId) throws Exception;

    void update(Order order);

    void delete(Integer id);

    List<Address> addressAll();
}
