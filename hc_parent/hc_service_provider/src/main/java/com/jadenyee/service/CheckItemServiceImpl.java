package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.CheckItemMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckItem;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    /**
     * 删除指定检测项
     * @param id 检测项 id
     * @return 返回执行结果
     * @throws Exception
     */
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

    /**
     * 查找指定 id 的检测项
     * @param id 检测项 id
     * @return 返回检测项信息
     */
    @Override
    public CheckItem findById(int id) {
        return mapper.findById(id);
    }

    /**
     * 查询检测项
     * @param bean 查询条件封装类
     * @return 返回查询结果信息
     */
    @Override
    public PageResult findByItem(QueryPageBean bean) {
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        String queryString = bean.getQueryString();
        if (!Objects.nonNull(queryString)) {
            queryString = "";
        }
        PageHelper.startPage(currentPage,pageSize);
        queryString = "%"+queryString.trim()+"%";
        Page<CheckItem> pageRes = mapper.findByCondition(queryString);
        return new PageResult(pageRes.getTotal(),pageRes.getResult());
    }

    /**
     * 更新检测项
     * @param item 需要更新的检测项信息
     * @return 返回操作执行结果
     */
    @Override
    public boolean updateCheckItem(CheckItem item) {

        return mapper.update(item);
    }

    /**
     * 查询所有的检测项信息
     * @return 返回查询结果
     */
    @Override
    public PageResult findAll() {
        List<CheckItem> all = mapper.findAll();
        return new PageResult((long) all.size(),all);
    }

}
