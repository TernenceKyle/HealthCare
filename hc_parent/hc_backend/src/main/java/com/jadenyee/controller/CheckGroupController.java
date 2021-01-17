package com.jadenyee.controller;

import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService service;

    @PostMapping("/grouplist")
    public PageResult getGroupList(@RequestBody QueryPageBean bean) {
        return service.findByContiditon(bean);
    }
}
