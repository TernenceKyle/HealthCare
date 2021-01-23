package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.constant.RedisMessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.OrderService;
import com.jadenyee.service.SetmealService;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService service;
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 移动端 访问套餐列表的信息
     *
     * @return
     */
    @PostMapping("/getSetmeal")
    public Result getSetmealList() {
        try {
            List<Setmeal> all = service.getAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, all);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @PostMapping("/findById")
    public Result getDetailById(Integer id) {
        try {
            Setmeal setmealById = service.getSetmealById(id);
            return new Result(true, "", setmealById);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "无法获取套餐信息!");
        }
    }

    /**
     * 移动端预约功能
     *
     * @param orderInfo 移动端发送到后端的信息
     * @return 返回一个结果封装
     */
    @PostMapping("/order")
    public Result orderSetmeal(@RequestBody Map orderInfo) {
        String telephone = (String) orderInfo.get("telephone");
        String validateCode = (String) orderInfo.get("validateCode");
        //校验手机号和验证码是否匹配
        if (StringUtils.isNotBlank(telephone) && StringUtils.isNotBlank(validateCode)) {
            String code = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone);
            if (code == null) return new Result(false, "验证码已失效，请重新发送!");
            if (!validateCode.equals(code)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            try {
                return orderService.orderSetmeal(Order.ORDERTYPE_WEIXIN,orderInfo);
            } catch (ParseException e) {
                return new Result(false,MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
        } else {
            return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
        }

    }
}
