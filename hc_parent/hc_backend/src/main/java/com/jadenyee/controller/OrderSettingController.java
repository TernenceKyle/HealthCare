package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.OrderSetting;
import com.jadenyee.service.OrderSettingService;
import com.jadenyee.utils.POIUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预约设置 Controller
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService service;

    /**
     *实现读取表格文件后的批量插入
     * @param excel 上传的表格文件
     * @return 操作响应结果
     */
    @PostMapping("/upload")
    public Result uploadExcel(@RequestParam("excelFile") MultipartFile excel){
        try {
            //使用 POI 工具类读取表格数据的内容
            List<String[]> strings = POIUtils.readExcel(excel);
            //使用 hashMap 进行相同日期的累加，并去重
            HashMap<Date,Integer> order = new HashMap<>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            for (String[] data : strings) {
                Date key = format.parse(data[0]);
                if (order.containsKey(key)) {
                    order.put(key, order.get(key)+Integer.valueOf(data[1]));
                }else {
                    order.put(key,Integer.valueOf(data[1]));
                }
            }
            //将数据封装为 List<OrderSetting> 集合
            List<OrderSetting> orderList = new ArrayList<>();
            order.forEach((date,number)->{
                OrderSetting os = new OrderSetting();
                os.setOrderDate(date);
                os.setNumber(number);
                orderList.add(os);
            });
            //将封装后的数据传输到业务层进行数据导入
            service.importOrderSettings(orderList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据给定日期获取预约schedule
     * @param date 当前月的日期
     * @return 当前月的所有预约信息
     */
    @PostMapping("/monthSchedule")
    public List<Map> getOrdersettingByMonth(@RequestBody Date date){
        date.setDate(1);
        Date start = (Date) date.clone();
        date.setDate(31);
        Date end = (Date) date.clone();
        List<OrderSetting> ordersettings = service.getOrdersettings(start, end);
        List<Map> res = new ArrayList<>();
        for (OrderSetting ordersetting : ordersettings) {
            Map orderMap = new HashMap();
            orderMap.put("date",ordersetting.getOrderDate().getDate());
            orderMap.put("number",ordersetting.getNumber());
            orderMap.put("reservations",ordersetting.getReservations());
            res.add(orderMap);
        }
        return res;
    }

    /**
     * 预约申请
     * @param orderSetting 预约日期
     * @return 返回执行结果封装
     */
    @PostMapping("/order")
    public Result setOrder(@RequestBody OrderSetting orderSetting){
        boolean b = service.setOrder(orderSetting);
        if (b){
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }
        return new Result(false,MessageConstant.ORDERSETTING_FAIL);
    }
}
