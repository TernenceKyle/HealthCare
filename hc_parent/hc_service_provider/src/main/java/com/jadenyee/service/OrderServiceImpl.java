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
import java.util.Date;
import java.util.Map;

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
                System.out.println(submit);
                newOrder = new Order(submit.getId(), date, Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, setmealID);
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
        // 将查询出的orderDate 日期数据格式化为字符串。
        SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        orderDetail.put("orderDate",format.format(orderDetail.get("orderDate")));
        return orderDetail;
    }
}
