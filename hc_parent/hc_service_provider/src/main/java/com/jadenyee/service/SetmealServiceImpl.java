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

    /**
     * 套餐业务
     * @param bean 查询条件封装类
     * @return 返回查询套餐分页信息
     */
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
            queryString = "%" + queryString.trim() + "%";
        }
        Page<Setmeal> res = mapper.findByCondition(queryString);
        return new PageResult(res.getTotal(), res.getResult());
    }

    /**
     * 删除套餐功能
     * @param id 需要删除的套餐 id
     * @return 返回操作执行结果
     */
    @Override
    public boolean deleteSetmeal(Integer id) {
        boolean delbind = mapper.deleteCheckGroups(id);
        boolean delSetmeal = mapper.delete(id);
        return delbind && delSetmeal;
    }

    /**
     * 更新套餐设置
     * @param setmeal 需要更新的套餐内容
     * @param groupIds 套餐关联的检测组
     * @return 返回执行结果
     */
    @Override
    public boolean updateSetmeal(Setmeal setmeal, Integer[] groupIds) {
        return false;
    }

    /**
     * 新增检查套餐
     * @param setmeal 新增的检测套餐内容
     * @param groupIds 与套餐相关联的检测组
     * @return 返回操作执行结果
     */
    @Override
    public boolean addSetmeal(Setmeal setmeal, Integer[] groupIds) {
        boolean addS = false;
        boolean addBind = false;
        if (Objects.nonNull(groupIds) && groupIds.length > 0) {
            addS = mapper.add(setmeal);
            addBind = mapper.addCheckGroups(setmeal.getId(), groupIds);
            return addS&addBind;
        } else {
            addS = mapper.add(setmeal);
            return addS;
        }
    }

    /**
     * 查找指定id 的检测套餐
     * @param id 检测套餐的id
     * @return 返回套餐信息
     */
    @Override
    public Setmeal getSetmealById(Integer id) {
        return mapper.findById(id);
    }

    /**
     * 查询指定套餐相关连的检测组
     * @param sid 指定的检测套餐id
     * @return 返回相关联的检测组id数组
     */
    @Override
    public Integer[] getGroups(Integer sid) {
        return mapper.getCheckGroups(sid);
    }

    /**
     * 查询所有检测套餐
     * @return 返回所有检测套餐信息
     */
    @Override
    public List<Setmeal> getAll() {
        return mapper.findAll();
    }
}
