package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.UserOperationLog;

import java.util.List;

public interface LogService {
    boolean log(UserOperationLog log);
    List<UserOperationLog> getAllLogs();
    PageResult getLogsByPage(QueryPageBean bean);
}
