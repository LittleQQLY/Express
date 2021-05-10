package com.QQLY.controller;

import com.QQLY.pojo.Address;
import com.QQLY.service.AddressService;
import com.QQLY.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("address")
@Component
public class AddressController {
    //地址簿管理，简单的增删改查

    @Autowired
    private AddressService addressService;

    @PostMapping("/save")
    @ResponseBody
    public JSONResult save(String username,String address){

        addressService.addAddress(username,address);
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public JSONResult delete(String username,String address){

        addressService.deleteAddress(username,address);
        return JSONResult.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public JSONResult update(String username,String address){

        addressService.updateAddress(username,address);
        return JSONResult.ok();
    }

    @PostMapping("/select")
    @ResponseBody
    public JSONResult select(String username){
        List<Address> list=addressService.selectAll(username);
        return JSONResult.ok(list);
    }
}
