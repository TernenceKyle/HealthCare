package com.jadenyee.controller;

import com.jadenyee.entity.Result;
import com.jadenyee.pojo.Address;
import com.jadenyee.service.AddressService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AdressController {
    @Reference
    private AddressService service;

    @GetMapping("/list")
    public Result getAddressList() {
        try {
            List<Address> addresses = service.addressList();
            return new Result(true,"获取地址列表成功",addresses);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "获取地址列表失败");
        }
    }
}
