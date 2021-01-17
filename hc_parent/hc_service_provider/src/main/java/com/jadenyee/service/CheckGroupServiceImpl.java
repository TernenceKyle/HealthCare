package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.CheckGroupMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckGroup;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
@Service
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupMapper mapper;
    @Override
    public boolean addCheckGroup(CheckGroup Group) {
        return false;
    }

    @Override
    public boolean deleteCheckGroup(int id) throws Exception {
        return false;
    }

    @Override
    public CheckGroup findById(int id) {
        return null;
    }
    /*
    CheckGroup 的分页查询
    * */
    @Override
    public PageResult findByContiditon(QueryPageBean bean) {
        String queryString = bean.getQueryString();
        if (!Objects.nonNull(queryString)){
            queryString = "";
        }
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> res = mapper.findByCondition(queryString.trim());
        return new PageResult(res.getTotal(), res.getResult());
    }

    @Override
    public boolean updateCheckGroup(CheckGroup Group) {
        return false;
    }
}
