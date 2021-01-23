package com.jadenyee.controller;

import com.aliyuncs.exceptions.ClientException;
import com.jadenyee.constant.MessageConstant;
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
}
