package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.PageResult;
import com.jadenyee.entity.QueryPageBean;
import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Setmeal;
import com.jadenyee.service.SetmealService;
import com.jadenyee.utils.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService service;


    @PostMapping("/upload")
    public Result uploadPic(@RequestParam("imgFile") MultipartFile file){
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf('.');
        String upFileName = UUID.randomUUID()+filename.substring(index-1);
        try {
            QiniuUtils.uploadFile("myhealthcare",file.getBytes(),upFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,upFileName);
    }


    @PostMapping("/list")
    public PageResult getSetmealList(@RequestBody QueryPageBean bean) {
        return service.getSetmealList(bean);
    }
    @PostMapping("/add")
    public Result addSetmeal(@RequestBody Setmeal setmeal,Integer[] cgIds){
        boolean res;
        try{
            res = service.addSetmeal(setmeal, cgIds);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(res,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    @GetMapping("/delete")
    public Result deleteSetmeal(Integer id){
        boolean res;
        try{
            res = service.deleteSetmeal(id);
        }catch (Exception e){
            return new Result(false, "删除套餐数据失败!");
        }
        return new Result(res,"删除套餐数据成功!");
    }
}
