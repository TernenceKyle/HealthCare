package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.LogMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.UserOperationLog;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public boolean log(UserOperationLog log) {
        return logMapper.add(log);
    }

    @Override
    public List<UserOperationLog> getAllLogs() {
        return logMapper.findAll();
    }

    @Override
    public PageResult getLogsByPage(QueryPageBean bean) {
        String queryString = bean.getQueryString();
        Integer pageSize = bean.getPageSize();
        Integer currentPage = bean.getCurrentPage();
        pageSize = pageSize==null?10:pageSize;
        currentPage = currentPage==null?1:currentPage;
        PageHelper.startPage(currentPage,pageSize);
        queryString = queryString==null?"":queryString;
        queryString = "%"+queryString+"%";
        Page<UserOperationLog> byCondition = logMapper.findByCondition(queryString);
        return new PageResult(byCondition.getTotal(),byCondition.getResult());
    }
}
