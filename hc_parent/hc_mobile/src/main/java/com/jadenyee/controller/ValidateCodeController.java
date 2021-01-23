package com.jadenyee.controller;

import com.aliyuncs.exceptions.ClientException;
import com.jadenyee.constant.MessageConstant;
import com.jadenyee.constant.RedisConstant;
import com.jadenyee.constant.RedisMessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Order;
import com.jadenyee.utils.SMSUtils;
import com.jadenyee.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 手机短信验证码发送
     * @param telephone 指定手机号
     * @return 返回执行结果
     */
    @GetMapping("/sendCode")
    public Result sendValidateCode(String telephone){
        String validCode = ValidateCodeUtils.generateValidateCode4String(4);
        System.out.println("手机号:"+telephone+" - 验证码: "+validCode);
//        try {
//            SMSUtils.sendShortMessage("",telephone,validCode);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+":"+telephone,60,validCode);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 手机登录的短信验证码发送
     * @param telephone 需要进行登录的手机号
     * @return 返回操作结果值
     */
    @GetMapping("/sendLoginCode")
    public Result sendValidateCode4Login(String telephone){
        String loginCode = ValidateCodeUtils.generateValidateCode4String(4);
        // Redis 中存放登录用的手机验证码 格式为 key:方式:手机号 value:验证码
        jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN+":"+telephone,60,loginCode);
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
