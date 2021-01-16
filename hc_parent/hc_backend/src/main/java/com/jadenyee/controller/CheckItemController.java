package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.CheckItem;
import com.jadenyee.service.CheckItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/*
*  检查项管理
*/
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService service;
    @PostMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem item){
        try{
            service.addCheckItem(item);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    @RequestMapping("/test")
    public String testConn(){
        return "OK!";
    }
    @RequestMapping("/delete/{id}")
    public Result deleteCheckItem(@PathVariable Integer id){
        return service.deleteCheckItem(id)? new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS)
                :new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
    }
    @PostMapping("/itemlist")
    public PageResult itemList(@RequestBody QueryPageBean bean){
        List<CheckItem> byItem = null;
        try{
            return service.findByItem(bean);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new PageResult(0L, Collections.emptyList());
        }
    }
}
