package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.CheckItemMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

//当配置了 @Transactoinal 注解后，需要在 @Service 中声明当前实现类所实现的接口类class对象
@Service(interfaceClass = CheckItemService.class)
//@Transactional
public class CheckItemServiceImpl  implements CheckItemService{
    @Autowired
    private CheckItemMapper mapper;
    @Override
    public boolean addCheckItem(CheckItem item) {
        return mapper.add(item);
    }

    @Override
    public boolean deleteCheckItem(int id) {
        return mapper.delete(id);
    }

    @Override
    public List<CheckItem> findByPage(int page, int size) {
        PageHelper.startPage(page,size);
        return mapper.findAll();
    }

    @Override
    public CheckItem findById(int id) {
        return mapper.findById();
    }

    @Override
    public PageResult findByItem(QueryPageBean bean) {
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        String queryString = bean.getQueryString();

        if (!Objects.nonNull(queryString)) queryString = "";

        if (Objects.nonNull(currentPage)&&Objects.nonNull(pageSize)){
            PageHelper.startPage(currentPage,pageSize);
            Page<CheckItem> pageRes = mapper.findByCondition(queryString.trim());
            return new PageResult(pageRes.getTotal(),pageRes.getResult());
        }
        PageHelper.startPage(1,10);
        Page<CheckItem> byCondition = mapper.findByCondition(null);
        return new PageResult(byCondition.getTotal(),byCondition.getResult());
    }

    @Override
    public boolean updateCheckItem(CheckItem item) {

        return mapper.update(item);
    }

}
