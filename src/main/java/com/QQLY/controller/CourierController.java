package com.QQLY.controller;

import com.QQLY.pojo.CourierDetails;
import com.QQLY.pojo.CourierList;
import com.QQLY.pojo.Users;
import com.QQLY.service.CourierService;
import com.QQLY.service.UserService;
import com.QQLY.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//快递
@Component
@RequestMapping("courier")
public class CourierController {

    @Autowired
    private CourierService courierService;

    @Autowired
    private UserService userService;

    @PostMapping("/addCourier")
    @ResponseBody
    public JSONResult addCourier(String num,String value,String username){
        CourierDetails courierDetails=new CourierDetails();
        courierDetails.setNum(num);
        courierDetails.setUsername(username);
        courierDetails.setValue(value);
        courierDetails.setTime1(new Date());
        courierService.saveCourier(courierDetails);
        //创建通证，并保存在快递信息表中
        //未实现

        return JSONResult.ok();
    }

    @PostMapping("/selectCourier")
    @ResponseBody
    public JSONResult selectCourier(String username){
        //根据身份查询快递
        Users users=userService.queryUserInfoByUsername(username);
        List<CourierList> lists=new ArrayList();
        if(users.getPower()=="1"){
            lists=courierService.selectAll();
        }else{
            lists=courierService.selectByUsername(username);
        }
        return JSONResult.ok(lists);
    }

    @PostMapping("/deleteCourier")
    @ResponseBody
    public JSONResult deleteCourier(String num){
        CourierDetails courierDetails=courierService.selectById(num);
        if(courierDetails.getTime2()==null){
            return JSONResult.errorMsg("快递暂未取走，还不能删除哦");
        }else if(courierDetails==null){
            return JSONResult.errorMsg("快递不存在");
        }else{
            //删除通证
            //未实现

            courierService.deleteCourier(num);
            return JSONResult.ok("删除成功");
        }
    }



}
