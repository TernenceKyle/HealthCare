package com.jadenyee.service;

import com.jadenyee.dao.OrderSettingMapper;
import com.jadenyee.pojo.OrderSetting;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 预约设置 Service
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingMapper mapper;

    /**
     * 进行数据的导入
     * @param lists 传入需要导入的数据
     */
    @Override
    public void importOrderSettings(List<OrderSetting> lists) {
        for (OrderSetting os : lists) {
            OrderSetting byDate = mapper.findByDate(os.getOrderDate());
            if (Objects.nonNull(byDate)) {
                os.setId(byDate.getId());
                mapper.update(os);
                continue;
            }
            mapper.add(os);
        }
    }

    /**
     * 查询在给定日期范围内的预约信息
     * @param start 开始日期
     * @param end  结束日期
     * @return  预约信息列表
     */
    @Override
    public List<OrderSetting> getOrdersettings(Date start, Date end) {
        return mapper.findByRange(start,end);
    }

    /**
     * 预约功能
     * @param os 预约日期
     * @return  执行结果
     */
    @Override
    public boolean setOrder(OrderSetting os) {
        if (Objects.nonNull(os)) {
            Date orderDate = os.getOrderDate();
            OrderSetting byDate = mapper.findByDate(orderDate);
            if (Objects.nonNull(byDate)){
                Integer id = byDate.getId();
                os.setId(id);
                mapper.update(os);
            }else {
                mapper.add(os);
            }
            return true;
        }
        return false;
    }

    /**
     * 根据日期查询预约信息
     * @param date 指定日期
     * @return 当天的预约情况
     */
    @Override
    public OrderSetting getOrderSettingByDate(Date date) {
        return mapper.findByDate(date);
    }
}
