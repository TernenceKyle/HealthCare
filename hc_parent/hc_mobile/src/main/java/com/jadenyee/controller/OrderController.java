package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Member;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.MemberService;
import com.jadenyee.service.OrderService;
import com.jadenyee.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *体检预约Controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService service;
    /**
     * 获取预约的详细信息
     * @param id 预约的 id
     * @return 返回预约详细信息的封装
     */
    @PostMapping("/detail")
    public Result getOrderById(Integer id){
        try{
            Map orderInfo = service.getOrderDetail(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
