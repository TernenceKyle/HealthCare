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
    public boolean deleteCheckItem(int id) throws Exception {
        //检查当前检测箱是否被检查组关联
        int bind = mapper.getGroupBindCount(id);
        //被关联的检查组无法被删除
        if (bind>0){
            //抛出异常终止删除操作
            throw new Exception("该检测项已被检测组关联！不能删除！");
        }
        return mapper.delete(id);
    }

    @Override
    public CheckItem findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public PageResult findByItem(QueryPageBean bean) {
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        String queryString = bean.getQueryString();
        if (!Objects.nonNull(queryString)) {
            queryString = "";
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> pageRes = mapper.findByCondition(queryString.trim());
        System.out.println(pageRes.getResult());
        return new PageResult(pageRes.getTotal(),pageRes.getResult());
    }

    @Override
    public boolean updateCheckItem(CheckItem item) {

        return mapper.update(item);
    }

    @Override
    public PageResult findAll() {
        List<CheckItem> all = mapper.findAll();
        return new PageResult((long) all.size(),all);
    }

}
