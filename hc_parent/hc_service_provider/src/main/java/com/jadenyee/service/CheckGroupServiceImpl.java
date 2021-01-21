package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.CheckGroupMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.CheckGroup;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupMapper mapper;

    @Override
    public List<CheckGroup> findAll() {
        return mapper.findAll();
    }

    @Override
    public CheckGroup findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public int[] getItems(int gid) {
        return mapper.getBindItems(gid);
    }

    /*
    CheckGroup 的分页查询
    * */
    @Override
    public PageResult findByContiditon(QueryPageBean bean) {
        String queryString = bean.getQueryString();
        if (!Objects.nonNull(queryString)) {
            queryString = "";
        }
        Integer currentPage = bean.getCurrentPage();
        Integer pageSize = bean.getPageSize();
        PageHelper.startPage(currentPage, pageSize);
        queryString = "%" + queryString.trim() + "%";
        Page<CheckGroup> res = mapper.findByCondition(queryString);
        return new PageResult(res.getTotal(), res.getResult());
    }

    /**
     * 新增检测组
     * @param group
     * @param ids 关联的检测项 id
     * @return 返回操作执行结果
     */
    @Override
    public boolean addCheckGroup(CheckGroup group, Integer[] ids) {
        boolean add = mapper.add(group);
        boolean bind = false;
        if (Objects.nonNull(ids)&&ids.length>0) {
            bind = mapper.addBind(group.getId(), ids);
        } else {
            bind = true;
        }
        return (add && bind);
    }

    /**
     * 更新检测组
     * @param Group 需要更新的检测组
     * @param ids 关联的检测项 id
     * @return 返回操作执行结果
     */
    @Override
    public boolean updateCheckGroup(CheckGroup Group, Integer[] ids) {
        if (Objects.nonNull(ids)&&ids.length>0){
            mapper.deleteBind(Group.getId());
            return mapper.update(Group) && mapper.addBind(Group.getId(), ids);
        }else {
            mapper.deleteBind(Group.getId());
            return mapper.update(Group);
        }
    }

    /**
     * 删除检测组
     * @param id 需要删除的检测组 id
     * @return 返回操作执行结果
     * @throws Exception
     */
    @Override
    public boolean deleteCheckGroup(int id) throws Exception {
        return (mapper.deleteBind(id)&&mapper.delete(id));
    }
}
