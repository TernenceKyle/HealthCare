package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.AddressMapper;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.Address;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressMapper mapper;

    /**
     * 根据查询条件的封装查询地址信息
     * @param bean
     * @return
     */
    @Override
    public Page<Address> getAddressByPage(QueryPageBean bean) {
        PageHelper.startPage(bean.getCurrentPage(),bean.getPageSize());
        Page<Address> byQueryString = mapper.findByQueryString(bean.getQueryString());
        return byQueryString;
    }

    /**
     * 获取所有的 地址列表
     * @return 地址列表的封装
     */
    @Override
    public List<Address> addressList() {
        return mapper.findAll();
    }

    /**
     * 更新 地址
     * @param address
     * @return 执行结果
     */
    @Override
    public boolean updateAddress(Address address) {
        return mapper.update(address);
    }

    /**
     * 删除地址
     * @param id 地址 id
     * @return 执行结果
     */
    @Override
    public boolean deleteAddress(Integer id) {
        return mapper.delete(id);
    }

    /**
     * 新增地址
     * @param address 新增地址的数据内容
     * @return 操作结果
     */
    @Override
    public boolean addAddress(Address address) {
        return mapper.add(address);
    }
}
