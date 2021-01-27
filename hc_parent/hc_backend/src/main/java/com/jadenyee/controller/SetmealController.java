package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.constant.RedisConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.SetmealService;
import com.jadenyee.utils.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.UUID;

/**
 * 套餐业务 Controller
 *
 * @author jadenYee
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService service;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 图片上传功能
     *
     * @param file 上传的图片资源
     * @author jadenYee
     */
    @PostMapping("/upload")
    public Result uploadPic(@RequestParam("imgFile") MultipartFile file) {
        String filename = file.getOriginalFilename();
        //上传文件名的非空判断
        if (Objects.nonNull(filename) && filename.length() > 0) {
            int index = filename.lastIndexOf('.');
            //生成存储文件名，UUID+有效文件后缀名
            String upFileName = UUID.randomUUID() + filename.substring(index);
            try {
                //使用七牛云工具类，上传文件数据到，对象存储空间中。
                boolean res = QiniuUtils.uploadFile("myhealthcare", file.getBytes(), upFileName);
                //上传成功
                if (res) {
                    jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, upFileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
            }
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, upFileName);
        } else {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 分页查询方法
     *
     * @param bean 传入查询列表的封装对象
     * @return 返回页面封装信息
     */
    @PostMapping("/list")
    public PageResult getSetmealList(@RequestBody QueryPageBean bean) {
        return service.getSetmealList(bean);
    }

    /**
     * 套餐添加功能
     *
     * @param setmeal 套餐表单数据
     * @param cgIds   套餐包含的检查组数组
     * @author jadenYee
     */
    @PostMapping("/add")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] cgIds) {
        boolean res;
        try {
            res = service.addSetmeal(setmeal, cgIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        if (res) {
            //如果添加成功，那么将该图片信息存入到 Redis 缓存中的 setmealPicDBResources 有效的图片数据。
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        }
        return new Result(res, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 更新修改后的套餐内容数据
     *
     * @param setmeal 更新的套餐
     * @param cgIds   与套餐相关联的
     * @return 返回操作结果
     */
    @PostMapping("/update")
    public Result updateSetmeal(@RequestBody Setmeal setmeal, Integer[] cgIds) {
        boolean res;
        try {
            res = service.updateSetmeal(setmeal, cgIds);
            if (res) {
                jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            }
            return new Result(true, "修改套餐内容成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 删除方法
     *
     * @param id 传入删除 setmeal 的id
     * @return 返回一个业务执行结果
     */
    @GetMapping("/delete")
    public Result deleteSetmeal(Integer id) {
        boolean res;
        try {
            res = service.deleteSetmeal(id);
        } catch (Exception e) {
            return new Result(false, "删除套餐数据失败!");
        }
        return new Result(res, "删除套餐数据成功!");
    }

    /**
     * 获取套餐信息（用于修改）
     *
     * @param id 套餐 id
     * @return 套餐内容信息
     */
    @GetMapping("/get")
    public Result getSetmealById(Integer id) {
        try {
            Setmeal setmeal4Edit = service.getSetmeal4Edit(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal4Edit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 获取该套餐包含的检测组
     *
     * @param id 给定的套餐 id
     * @return 返回检测组id 数组
     */
    @GetMapping("/getCheckGroups")
    public Integer[] getCheckGroups(Integer id) {
        return service.getGroups(id);
    }
}
