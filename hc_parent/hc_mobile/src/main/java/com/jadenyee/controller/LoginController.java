package com.jadenyee.controller;

import com.alibaba.fastjson.JSON;
import com.jadenyee.constant.MessageConstant;
import com.jadenyee.constant.RedisMessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Member;
import com.jadenyee.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.CollationKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 移动端登录 Controller
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService service;

    /**
     * 手机短信验证登录
     * @param loginInfo 登录信息
     * @param response  Http响应，存储登录信息的手机号到客户端，有效期三十天
     * @return 操作执行结果
     */
    @PostMapping("/check")
    public Result loginCheck(@RequestBody Map loginInfo, HttpServletResponse response){
        //校验验证码
        String telephone =(String) loginInfo.get("telephone");
        String validateCode = (String) loginInfo.get("validateCode");
        String codeInRedis = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone);
        if (codeInRedis==null) return new Result(false, "验证码已过期！请重新登录!");
        if (!codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //查询会员信息（根据手机号）
        Member member = service.findMember(telephone);
        //如果为新用户则自动注册
        if (member==null){
            Member newMember = new Member();
            newMember.setPhoneNumber(telephone);
            newMember.setRegTime(new Date());
            if (!service.submit(newMember)) {
                return new Result(false,"登录失败，请稍后再试");
            }
        }
        //将用户的登陆手机号以 Cookie 的形式保存再客户端
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
        String memberJson  = JSON.toJSONString(member);
        //将登录的会员信息存储在Redis中，有效时间三十分钟。
        jedisPool.getResource().setex(telephone, 30 * 60, memberJson);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
