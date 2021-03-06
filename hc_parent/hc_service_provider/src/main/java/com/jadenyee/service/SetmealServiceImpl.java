package com.jadenyee.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jadenyee.dao.SetmealMapper;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.pojo.Setmeal;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.*;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper mapper;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    //freemarker 生成的静态页面的路径
    @Value("${out_put_path}")
    private String outputPath;
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
        this.generateMobileStaticHtml();
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
        try {
            generateWhenDel(id);
        }catch (Exception e){
            e.printStackTrace();
        }
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
        boolean deleteBind = mapper.deleteCheckGroups(setmeal.getId());
        boolean addBind = mapper.addCheckGroups(setmeal.getId(), groupIds);
        boolean update = mapper.update(setmeal);
        generateWhenChange(setmeal.getId());
        return deleteBind&&addBind&&update;
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
            generateWhenChange(setmeal.getId());
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
     * 根据id 仅获取 Setmeal 相关的数据
     * @param id 给定的 id
     * @return 返回对应的 setmeal 套餐内容
     */
    @Override
    public Setmeal getSetmeal4Edit(Integer id) {
        return mapper.getById(id);
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

    /**
     * 套餐预约信息的数据统计
     * @return 返回消息封装
     */
    @Override
    public Map<String,List> getSetmealStatistics() {
        List<Map<String,Object>> res = mapper.getSetmealStatisticByOrder();
        List<String> nameList = new ArrayList<>();
        List<Map<String,Object>> valueList = new ArrayList<>();
        Map<String,List> result = new HashMap<>();
        String key = null;
        for (Map<String,Object> re : res) {
           nameList.add((String) re.get("name"));
           valueList.add(re);
        }
        result.put("setmealNames",nameList);
        result.put("setmealCount",valueList);
        return result;
    }

    /**
     * 获取热点套餐的统计数据
     * @return 返回结果封装
     */
    @Override
    public List<Map<String, Object>> getHotSetmealStat() {
        return mapper.getHotSetmealStat();
    }

    /**
     * 根据 ftl 模板生成静态的 html 文件
     */
    public void generateMobileStaticHtml(){
        System.out.println("套餐静态文件生成...");
        /*获取所有的套餐信息用于生成静态页面*/
        List<Setmeal> all = this.getAll();
        //生成套餐列表的静态页面
        generateMobileSetmealListHtml(all);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(all);
    }
    /**
     * 生成预约套餐列表静态页面
     * @param all 预约套餐列表信息
     * @date 2021/1/21
     */
    public void generateMobileSetmealDetailHtml(List<Setmeal> all) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("setmealList",all);
        this.generateHtml("mobile_setmeal.ftl","m_setmeal.html",dataMap);
    }

    /**
     * 生成预约套餐详情静态页面
     * @param all 所有的套餐列表信息
     * @date 2021/1/21
     */
    public void generateMobileSetmealListHtml(List<Setmeal> all) {
        for (Setmeal setmeal : all) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal",this.mapper.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",dataMap);
        }
    }
    /**
     * 负责生成静态 html 页面
     * @param templateName ftl模板名字
     * @param htmlPageName 页面名
     * @param dataMap 生成页面所需要的数据Map集合
     * @date 2021/1/21
     */
    public void generateHtml(String templateName,String htmlPageName,Map<String,Object> dataMap){
        Configuration config = freeMarkerConfigurer.getConfiguration();
        File docFile = new File(outputPath+"\\"+htmlPageName);
        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));){
            Template template = config.getTemplate(templateName);
            template.process(dataMap,out);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 当添加新的套餐时，重新生成套餐页表静态页面，和新增套餐对应的静态页面
     * @param sid 套餐 id
     * @date  2021/1/26
     */
    public void generateWhenChange(Integer sid){
        List<Setmeal> all = this.getAll();
        generateMobileSetmealDetailHtml(all);
        for (Setmeal setmeal : all) {
            if (Objects.equals(sid,setmeal.getId())){
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("setmeal",this.mapper.findById(setmeal.getId()));
                this.generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",dataMap);
            }
        }
    }

    /**
     * 当某个套餐被删除时，重新生成套餐列表页面，并删除对应的套餐内容静态页面
     * @param sid 删除的套餐 id
     */
    public void generateWhenDel(Integer sid) throws IOException {
        this.generateMobileSetmealDetailHtml(this.getAll());
    }

}
