package com.jadenyee.controller;

import com.github.pagehelper.Page;
import com.jadenyee.annotations.Log;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Address;
import com.jadenyee.service.AddressService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AdressController {
    @Reference
    private AddressService service;

    /**
     * 获取所有的地址
     * @return 返回地址集合封装
     */
    @GetMapping("/list")
    @Log(title = "查询机构地址信息(地图预览)",operation = Log.OPERA_TYPE_SELECT)
    public Result getAddressList() {
        try {
            List<Address> addresses = service.addressList();
            return new Result(true,"获取地址列表成功",addresses);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取地址列表失败");
        }
    }

    /**
     * 地址列表分页查询
     * @param bean 封装的条件查询对象
     * @return 返回 PageRedult 封装对象
     */
    @PostMapping("/findPage")
    @Log(title = "查询地址列表信息",operation = Log.OPERA_TYPE_SELECT)
    public PageResult getAddressByBean(@RequestBody QueryPageBean bean){
        try {
            Page<Address> page = service.getAddressByPage(bean);
            return new PageResult(page.getTotal(),page.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(0L, Collections.EMPTY_LIST);
        }
    }
    /**
     * 添加地址信息
     * @param addr 封装的地址信息
     * @return 返回操作结果封装
     */
    @PostMapping("/add")
    @Log(title = "新增地点信息",operation = Log.OPERA_TYPE_EDIT)
    public Result addAddress(@RequestBody Address addr){
        try{
            boolean b = service.addAddress(addr);
            return b ? new Result(b, "新增地址成功!") : new Result(b, "新增地址失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"新增地址失败!");
        }
    }

    /**
     * 删除地址 根据id
     * @param id
     * @return 返回操作结果
     */
    @DeleteMapping("/delete")
    @Log(title = "删除地点信息",operation = Log.OPERA_TYPE_DEL)
    public Result delAddress(Integer id){
        try{
            System.out.println(this.getClass().getName()+" =>>> "+id);
            service.deleteAddress(id);
            return new Result(true,"删除地址成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除地址失败!");
        }
    }
}
