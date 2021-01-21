package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.SetmealService;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService service;

    /**
     * 移动端 访问套餐列表的信息
     * @return
     */
    @PostMapping("/getSetmeal")
    public Result getSetmealList() {
        try {
            List<Setmeal> all = service.getAll();
            return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,all);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }
    @PostMapping("/findById")
    public Result getDetailById(Integer id){
        try {
            Setmeal setmealById = service.getSetmealById(id);
            return new Result(true,"",setmealById);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"无法获取套餐信息!");
        }
    }

}
