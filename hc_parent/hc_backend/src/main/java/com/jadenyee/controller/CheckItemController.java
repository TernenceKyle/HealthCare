package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.CheckItem;
import com.jadenyee.service.CheckItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 检查项 Controller
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService service;

    /**
     * 新增检测项
     * @param item 需要新增的检测项pojo
     * @return 返回执行结果封装对象
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result addCheckItem(@RequestBody CheckItem item) {
        try {
            service.addCheckItem(item);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除指定 id 的检测项
     * @param item 需要删除的检测项
     * @return 返回操作的执行结果
     * @throws Exception
     */

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result deleteCheckItem(@RequestBody CheckItem item) throws Exception {
        try {
            service.deleteCheckItem(item.getId());
        }catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 检测项的分页查询
     * @param bean 分页查询条件封装 bean
     * @return 返回分页查询页面信息
     */
    @PostMapping("/itemlist")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public PageResult itemList(@RequestBody QueryPageBean bean) {
        List<CheckItem> byItem = null;
        try {
            return service.findByItem(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, Collections.emptyList());
        }
    }

    /**
     * 获取指定id的检测项数据
     * @param id 检测项id
     * @return 返回检测箱信息
     */
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
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

    /**
     * 更新检测项
     * @param item 更改后的检测项
     * @return 执行结果封装对象
     */
    @PreAuthorize("hasAuthority('CHECKITEM_UPDATE')")
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

    /**
     * 获取所有的检测项信息
     * @return 返回信息结果
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @GetMapping("/items")
    public PageResult getAllItems(){
        return service.findAll();
    }
}
