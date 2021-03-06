package com.jadenyee.service;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.dao.OrderMapper;
import com.jadenyee.dao.OrderSettingMapper;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Member;
import com.jadenyee.pojo.Order;
import com.jadenyee.pojo.OrderSetting;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Order 预约申请
 * @author JadenYee
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingMapper osMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 预约套餐操作
     *
     * @param plateform 预约的渠道
     * @param orderInfo 预约信息
     * @return 操作结果封装对象
     */
    @Override
    public Result orderSetmeal(String plateform, Map orderInfo) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse((String) orderInfo.get("orderDate"));
        OrderSetting orderSetting = osMapper.findByDate(date);
        //如果选择的日期没有进行设置，则拒绝预约
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //如果选择的日期已经被预约满了，则拒绝预约
        if ((orderSetting.getNumber() == orderSetting.getReservations())) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //根据手机号查询对应的会员信息，如果不存在则自动注册会员。
        String tel = (String) orderInfo.get("telephone");
        Member member = memberService.findMember(tel);
        Integer setmealID = Integer.valueOf((String) orderInfo.get("setmealId"));
        //由于特殊原因选中的 地址id 为int 类型可以直接进行强转
        int addressID = (int)orderInfo.get("addressId");
        Order newOrder;
        if (member == null) {
            Member submit = new Member();
            submit.setName((String) orderInfo.get("name"));
            submit.setSex((String) orderInfo.get("sex"));
            submit.setIdCard((String) orderInfo.get(("idCard")));
            submit.setPhoneNumber(tel);
            submit.setRegTime(new Date());
            //会员注册成功后，进行新增套餐预约操作
            if (memberService.submit(submit)) {
                newOrder = new Order(submit.getId(), date, Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, setmealID,addressID);
                if (orderMapper.add(newOrder)){
                    orderSetting.setReservations(orderSetting.getReservations()+1);
                    osMapper.update(orderSetting);
                    //返回成功预约id
                    return new Result(true,MessageConstant.ORDER_SUCCESS,newOrder.getId());
                }
                return new Result(false,MessageConstant.ORDER_FULL);
            }
        }
        //判断是否已经预约过了
        Order res = orderMapper.findByMemberAndDate(member.getId(), date);
        if (res != null) {
            return new Result(false, MessageConstant.HAS_ORDERED);
        }
        //新增预约
        newOrder = new Order(member.getId(),date,Order.ORDERTYPE_WEIXIN,Order.ORDERSTATUS_NO,setmealID);
        if (orderMapper.add(newOrder)){
            orderSetting.setReservations(orderSetting.getReservations()+1);
            osMapper.update(orderSetting);
            return new Result(true,MessageConstant.ORDER_SUCCESS,newOrder.getId());
        }
        return new Result(false,MessageConstant.ORDER_FULL);
    }

    /**
     * 根据 id 查询相关的预约订单信息
     * @param id order 的id
     * @return 返回预约信息
     */
    @Override
    public Order findOrder(Integer id) {
        return orderMapper.findById(id);
    }

    /**
     * 预约成功后展示给用户的预约信息
     * @param id 给定 Order 的 ID
     * @return 返回一个封装了数据的 Map 集合
     */
    @Override
    public Map<String, String> getOrderDetail(Integer id) {
        Map orderDetail = orderMapper.getOrderDetail(id);
        //避免空指针异常
        if (Objects.nonNull(orderDetail)) {
            // 将查询出的orderDate 日期数据格式化为字符串。
            SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
            orderDetail.put("orderDate", format.format(orderDetail.get("orderDate")));
        }
        return orderDetail;
    }

    /**
     * 获取当前时间为准的一月、一周、一天内的预约统计数据
     * @return 返回结果封装集
     */
    @Override
    public Map<String, Integer> getOrderStat() {
        LocalDate date = LocalDate.now();
        //本周周一日期
        int nowOfWeek = date.get(ChronoField.DAY_OF_WEEK);
        LocalDate startOfWeek = date.minusDays( nowOfWeek - 1);
        //本月第一天
        int dayOfMonth = date.get(ChronoField.DAY_OF_MONTH);
        LocalDate startOfMonth = date.minusDays(dayOfMonth - 1);
        Integer orderToday = orderMapper.countByDate(date.toString());
        Integer orderToWeek = orderMapper.countByPeriod(startOfWeek.toString(), date.toString());
        Integer orderToMonth = orderMapper.countByPeriod(startOfMonth.toString(), date.toString());
        Integer orderFinishedToday = orderMapper.countFinishedByDate(date.toString());
        Integer orderFinishedToWeek = orderMapper.countFinishedByPeriod(startOfWeek.toString(), date.toString());
        Integer orderFinishedToMonth = orderMapper.countFinishedByPeriod(startOfMonth.toString(),date.toString());
        Map<String,Integer> resMap = new HashMap<>();
        resMap.put("todayOrderNumber",orderToday);
        resMap.put("thisWeekOrderNumber",orderToWeek);
        resMap.put("thisMonthOrderNumber", orderToMonth);
        resMap.put("todayVisitsNumber", orderFinishedToday);
        resMap.put("thisWeekVisitsNumber", orderFinishedToWeek);
        resMap.put("thisMonthVisitsNumber", orderFinishedToMonth);
        return resMap;
    }
}
