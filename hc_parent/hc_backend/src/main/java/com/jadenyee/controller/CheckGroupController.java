package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.CheckGroup;
import com.jadenyee.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService service;

    @PostMapping("/grouplist")
    public PageResult getGroupList(@RequestBody QueryPageBean bean) {
        return service.findByContiditon(bean);
    }
    @PostMapping("/saveGroup")
    //疑问一 为什么 不能够转换成 List?
    public Result saveCheckGroup(@RequestBody CheckGroup group,Integer[] ids){
        try{
            service.addCheckGroup(group,ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/delete")
    public Result deleteCheckGroup(Integer id){
        try {
            service.deleteCheckGroup(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    @PostMapping("/update")
    public Result updateCheckGroup(@RequestBody CheckGroup group,Integer[] ids){
        try {
            boolean b = service.updateCheckGroup(group, ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/get")
    public CheckGroup getGroupById(Integer id){
        try {
            return service.findById(id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("/getItems")
    public int[] getItems(Integer gid){
        int[] items = service.getItems(gid);
        return items;
    }
}
