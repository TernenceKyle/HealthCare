package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.SetmealMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.Setmeal;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper mapper;

    @Override
    public PageResult getSetmealList(QueryPageBean bean) {
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        String queryString = bean.getQueryString();
        if (Objects.nonNull(currentPage) && Objects.nonNull(pageSize)) {
            PageHelper.startPage(currentPage, pageSize);
        }
        if (!Objects.nonNull(queryString)) {
            queryString = "";
        } else {
            queryString = "%"+queryString.trim()+"%";
        }
        Page<Setmeal> res = mapper.findByCondition(queryString);
        return new PageResult(res.getTotal(),res.getResult());
    }

    @Override
    public boolean deleteSetmeal(Integer id) {
        return mapper.deleteCheckGroups(id)&&mapper.delete(id);
    }

    @Override
    public boolean updateSetmeal(Setmeal setmeal, Integer[] groupIds) {
        return false;
    }

    @Override
    public boolean addSetmeal(Setmeal setmeal, Integer[] groupIds) {
        boolean add = mapper.add(setmeal);
        boolean b = mapper.addCheckGroups(setmeal.getId(), groupIds);
        return add&&b;
    }

    @Override
    public Setmeal getSetmealById(Integer id) {
        return null;
    }

    @Override
    public Integer[] getGroups(Integer sid) {
        return new Integer[0];
    }

    @Override
    public List<Setmeal> getAll() {
        return mapper.findAll();
    }
}
