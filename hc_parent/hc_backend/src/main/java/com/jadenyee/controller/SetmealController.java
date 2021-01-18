package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService service;

    @PostMapping("/list")
    public PageResult getSetmealList(@RequestBody QueryPageBean bean) {
        return service.getSetmealList(bean);
    }
    @PostMapping("/add")
    public Result addSetmeal(@RequestBody Setmeal setmeal,Integer[] cgIds){
        boolean res;
        try{
            res = service.addSetmeal(setmeal, cgIds);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(res,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @GetMapping("/delete")
    public Result deleteSetmeal(Integer id){
        boolean res;
        try{
            res = service.deleteSetmeal(id);
        }catch (Exception e){
            return new Result(false, "删除套餐数据失败!");
        }
        return new Result(res,"删除套餐数据成功!");
    }
}
