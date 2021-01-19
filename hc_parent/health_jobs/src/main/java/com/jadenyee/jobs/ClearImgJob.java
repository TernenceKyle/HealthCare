package com.jadenyee.jobs;

import com.jadenyee.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 进行定时清理相关的工作，删除无效的垃圾图片的任务。设置为每40秒执行一次，失效图片的批量删除。
     */
    public void cleanImg(){
        Set<String> sdiff = jedisPool.getResource().sdiff("setmealPicResources", "setmealPicDBResources");
        if (Objects.nonNull(sdiff)){
            QiniuUtils.batchDelete("myhealthcare", sdiff.toArray(new String[0]));
        }
    }
}
