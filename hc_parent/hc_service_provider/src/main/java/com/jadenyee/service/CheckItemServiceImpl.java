package com.jadenyee.service;

import com.jadenyee.dao.CheckItemMapper;
import com.jadenyee.pojo.CheckItem;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
//当配置了 @Transactoinal 注解后，需要在 @Service 中声明当前实现类所实现的接口类class对象
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl  implements CheckItemService{
    @Autowired
    private CheckItemMapper mapper;
    @Override
    public boolean addCheckItem(CheckItem item) {
        return mapper.add(item);
    }

}
