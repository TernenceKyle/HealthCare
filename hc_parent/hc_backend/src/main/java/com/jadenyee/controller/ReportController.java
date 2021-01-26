package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.service.MemberService;
import com.jadenyee.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    /**
     * 获取会员的年度信息数据统计
     * @return 封装的数据集合
     */
    @GetMapping("/getMemberReport")
    public Result getMemberStatistics(){
        try{
            Map<String, List> res = memberService.getMemberStatistics4PastYear();
            return  new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,res);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try{
            Map<String, List> res = setmealService.getSetmealStatistics();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,res);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
}
