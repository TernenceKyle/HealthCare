package com.jadenyee.controller;



import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Address;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.OrderSettingListService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersettinglist")
public class OrderSettingListController {

    @Reference
    private OrderSettingListService orderSettingListService;

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) throws Exception {
        return orderSettingListService.findPage(queryPageBean);
    }
    //查询套餐信息
    @RequestMapping("/findSetmeal")
    public Result findSetmeal(){
        try{
            List<Setmeal> list=orderSettingListService.findSetmeal();
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    //查询地址信息
    @RequestMapping("/addressAll")
    public Result addressAll(){
        try{
            List<Address> list=orderSettingListService.addressAll();
            return new Result(true,"查询地址信息成功",list);
        }catch (Exception e){
            return new Result(false,"查询地址信息失败");
        }
    }
    //增加
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String,Object> map,Integer setmealId){
        try {
            Result result = orderSettingListService.add(map,setmealId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"未知错误");
        }
    }

    //修改
    @RequestMapping("/update")
    public Result update(Integer id){
        try{
            Order order=new Order();
            order.setId(id);
            order.setOrderStatus(Order.ORDERSTATUS_YES);
            orderSettingListService.update(order);
            return new Result(true,"已到诊");
        }catch (Exception e){
            return new Result(false,"修改失败");
        }
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            orderSettingListService.delete(id);
            return new Result(true,"删除预约");
        }catch (Exception e){
            return new Result(false,"删除失败");
        }
    }

}
