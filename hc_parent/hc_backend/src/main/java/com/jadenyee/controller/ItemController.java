package com.jadenyee.controller;

import com.jadenyee.entity.Result;
import com.jadenyee.pojo.CheckItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
*  检查项管理
*/
@RestController
@RequestMapping("/checkitem")
public class ItemController {
    @RequestMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem item){
        System.out.println(item.toString());
        return null;
    }
}
