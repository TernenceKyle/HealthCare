package com.jadenyee.controller;

import com.github.pagehelper.Page;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.UserOperationLog;
import com.jadenyee.service.LogService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志相关的Controller
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Reference
    LogService logService;

    /**
     * 查询所有用户的日志信息
     * @return
     */
    @RequestMapping("/allUserLog")
    public List<UserOperationLog> getAllLog(){
        return logService.getAllLogs();
    }
    @PostMapping("/userLogByPage")
    public PageResult getLogByPage(@RequestBody QueryPageBean bean)
    {
        try{
            return logService.getLogsByPage(bean);
        }catch (Exception e){
            e.printStackTrace();
            return new PageResult(0L,null);
        }
    }
}
