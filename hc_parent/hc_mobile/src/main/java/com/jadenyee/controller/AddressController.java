package com.jadenyee.controller;

import com.jadenyee.pojo.Address;
import com.jadenyee.service.AddressService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    private AddressService service;

    /**
     * 移动端获取地址列表
     * @return 返回地址列表集合
     */
    @GetMapping("/list")
    public List<Address> getAddressList() {
        return service.addressList();
    }
}
