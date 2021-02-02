package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.constant.MessageConstant;

import com.jadenyee.dao.MemberMapper;
import com.jadenyee.dao.OrderMapper;
import com.jadenyee.dao.OrderSettingListDao;
import com.jadenyee.dao.OrderSettingMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.*;
import com.jadenyee.utils.DateUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingListService.class)
@Transactional
public class OrderSettingListServiceImpl implements OrderSettingListService {
    @Autowired
    private OrderSettingListDao orderSettingListDao;

    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) throws Exception {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);//分页插件，类似拦截器
        Page<Map<String, Object>> page = orderSettingListDao.findByCondition(queryString);
        System.out.println(page);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findSetmeal() {
        return orderSettingListDao.findSetmeal();
    }

    @Override
    public Result add(Map<String, Object> map, Integer setmealId) throws Exception {
            //1.检查用户所选择的预约日期是否进行了预约设置，如果没有则无法进行预约
            String orderDate = map.get("orderDate").toString();
            OrderSetting orderSetting = orderSettingMapper.findByDate(DateUtils.parseString2Date(orderDate));
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            //2.查看当天预约是否已满
            int number = orderSetting.getNumber();//可预约人数
            int reservations = orderSetting.getReservations();//已预约人数
            if (reservations >= number) {
                //预约已满，不能预约
                return new Result(false, MessageConstant.ORDER_FULL);
            }
            //3.检查预约是否重复,查是否有这个会员
            Member member = memberMapper.findByTel(map.get("phoneNumber").toString());
            if (member != null) {
                //有会员再判断是否重复(判断将条件们封装成order对象)一个人在同一天不能预约同一个套餐
                Integer memberId = member.getId();//会员id
                Date order_Date = DateUtils.parseString2Date(orderDate);//预约日期
                Order order = new Order();
                order.setMemberId(memberId);
                order.setOrderDate(order_Date);
                order.setSetmealId(setmealId);
                //根据条件查询是否重复预约了
                List<Order> list = orderSettingListDao.findByConditionMove(order);
                if (list != null & list.size() > 0) {
                    //查到了说明有重复预约，则无法完成预约
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
                //有会员了不注册把该会员id赋值给预约map
                map.put("member_id",member.getId());
            } else {
                //4.注册会员
                map.put("member_id", null);
                map.put("regTime", new Date());
                String birthday = map.get("birthday").toString();
                map.put("birthday", DateUtils.parseString2Date(birthday));
                orderSettingListDao.add(map);
            }
            //5.开始预约
            String orderDate1 = map.get("orderDate").toString();
            map.put("orderDate", DateUtils.parseString2Date(orderDate1));
            map.put("orderType", Order.ORDERTYPE_TELEPHONE);//设置预约类型，电话预约
            map.put("orderStatus", Order.ORDERSTATUS_NO);//未到诊
            map.put("setmeal_id", setmealId);
            orderSettingListDao.addorder(map);

            //6.更新当日的已预约人数
            orderSetting.setReservations(orderSetting.getReservations() + 1);
            orderSettingListDao.editReservationsByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDER_SUCCESS);

    }

    //已就诊按钮
    @Override
    public void update(Order order) {
        orderSettingListDao.update(order);
    }

    //删除预约
    @Override
    public void delete(Integer id) {
        orderSettingListDao.delete(id);
    }

    @Override
    public List<Address> addressAll() {
        return orderSettingListDao.addressAll();
    }

}
