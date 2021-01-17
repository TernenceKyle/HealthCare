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
    public Result addCheckItem(@RequestBody CheckItem item) {
        try {
            service.addCheckItem(item);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/test")
    public String testConn() {
        return "OK!";
    }

    /*
     * 删除 Controller
     * */
    @RequestMapping("/delete")
    public Result deleteCheckItem(@RequestBody CheckItem item) throws Exception {
        try {
            service.deleteCheckItem(item.getId());
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PostMapping("/itemlist")
    public PageResult itemList(@RequestBody QueryPageBean bean) {
        List<CheckItem> byItem = null;
        try {
            return service.findByItem(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, Collections.emptyList());
        }
    }

    @GetMapping("/get")
    public Result getItemDetail(Integer id) {
        CheckItem byId = null;
        try {
            byId = service.findById(id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new Result(false, "获取检查项信息失败!");
        }
        return new Result(true,"", byId);
    }
    @PostMapping("/update")
    public Result updateCheckItem(@RequestBody CheckItem item){
        try{
            service.updateCheckItem(item);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
//    @GetMapping("/items")
//    public PageResult getAllItems(){
//        service
//    }
}
