package com.jadenyee.controller;

import com.jadenyee.annotations.Log;
import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.CheckGroup;
import com.jadenyee.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 检测组 Controller 处理器
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService service;

    /**
     * 获取指定id 的检测组
     * @param id 检测组的id
     * @return 查询记过
     */
    @GetMapping("/get")
    @Log(title = "查询检测组(指定ID)",operation = Log.OPERA_TYPE_SELECT)
    public CheckGroup getGroupById(Integer id){
        try {
            return service.findById(id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询所有的检测组
     * @return
     */
    @GetMapping("/groups")
    @Log(title = "查询检查组信息",operation = Log.OPERA_TYPE_SELECT)
    public List<CheckGroup> getAllGroups(){
        return service.findAll();
    }

    /**
     * 查询指定 id 检测组关联的检测项
     * @param gid
     * @return
     */
    @GetMapping("/getItems")
    public int[] getItems(Integer gid){
        return service.getItems(gid);
    }

    /**
     * 分页查询检测组
     * @param bean 查询条件封装对象
     * @return 返回分页查询信息
     */
    @PostMapping("/grouplist")
    @Log(title = "查询检测组列表信息",operation = Log.OPERA_TYPE_SELECT)
    public PageResult getGroupList(@RequestBody QueryPageBean bean) {
        return service.findByContiditon(bean);
    }

    /**
     * 新增检测组功能
     * @param group 传入需要添加的检测组
     * @param ids 关联的检测项 id 数组
     * @return 返回执行结果
     */
    @PostMapping("/saveGroup")
    //疑问一 为什么 不能够转换成 List?
    @Log(title = "新增检测组",operation = Log.OPERA_TYPE_EDIT)
    public Result saveCheckGroup(@RequestBody CheckGroup group,Integer[] ids){
        try{
            service.addCheckGroup(group,ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除指定id的检测组
     * @param id 需要删除的检测组 id
     * @return 返回执行结果封装对象
     */
    @GetMapping("/delete")
    @Log(title = "删除检测组",operation = Log.OPERA_TYPE_DEL)
    public Result deleteCheckGroup(Integer id){
        try {
            service.deleteCheckGroup(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 更新（编辑）检测组
     * @param group 更改后的检测组
     * @param ids 更改后的关联检测项 id 数组
     * @return 返回执行结果封装对象
     */
    @PostMapping("/update")
    @Log(title = "编辑检测组",operation = Log.OPERA_TYPE_EDIT)
    public Result updateCheckGroup(@RequestBody CheckGroup group,Integer[] ids){
        try {
            boolean b = service.updateCheckGroup(group, ids);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


}
