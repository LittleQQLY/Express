package com.QQLY.controller;

import com.QQLY.pojo.SendOrder;
import com.QQLY.pojo.Users;
import com.QQLY.service.OrderService;
import com.QQLY.service.UserService;
import com.QQLY.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    //创建寄件订单
    @PostMapping("/createSendToken")
    @ResponseBody
    public JSONResult createSend(String payname, String username, String address, String msg, String phone) throws Exception {
        Date date=new Date();
        SendOrder sendOrder=new SendOrder();
        sendOrder.setTime(date);
        sendOrder.setPayname(payname);
        sendOrder.setUsername(username);
        sendOrder.setAddress(address);
        sendOrder.setStatus("0");
        sendOrder.setMsg(msg);
        sendOrder.setPhone(phone);
        orderService.saveOrder(sendOrder);
        return JSONResult.ok("yes");
    }

    //根据权限查询寄件
    @PostMapping("/selectAllSend")
    @ResponseBody
    public JSONResult selectAllSend(String username){
        Users users=userService.queryUserInfoByUsername(username);
        //根据权限查询个人所有或所有
        if(users.getPower()=="1"){
            return JSONResult.ok(orderService.selectAllOrder());
        }else{
            return JSONResult.ok(orderService.selectOrderByPayname(username));
        }
    }

    //查询寄件详情
    @PostMapping("/selectByOrderId")
    @ResponseBody
    public JSONResult selectByOrderId(String orderId){
        SendOrder sendOrder=orderService.selectOrderById(orderId);
        return JSONResult.ok(sendOrder);
    }

    //删除寄件状态 要不要做用户假删除，管理员真删除？
    @PostMapping("/deleteOrder")
    @ResponseBody
    public JSONResult deleteOrder(String orderId){
        SendOrder sendOrder=orderService.selectOrderById(orderId);
        if(sendOrder.getStatus()=="1"){
            //1未存 2未处理 3已处理 0还未上门收取 10 已上门收取还未处理
            return JSONResult.errorMsg("寄件暂未存入，不能删除");
        }else if(sendOrder.getStatus()=="2"||sendOrder.getStatus()=="10"){
            return JSONResult.errorMsg("寄件暂未处里，不能删除");
        } else{
            orderService.deleteOrder(orderId);
            return JSONResult.ok();
        }
    }

    //用户获取寄件二维码
    @PostMapping("/getQr")
    @ResponseBody
    public JSONResult getQr(String orderId){
        SendOrder sendOrder=orderService.selectOrderById(orderId);
        if(sendOrder.getStatus()=="1"){
            //用户还未将寄件存入，返回二维码开门存寄件,获取后状态改变，不能再获取
            //图片用啥
            orderService.updateOrder(orderId,"2");
            return JSONResult.ok();//返回图片
        }
        return JSONResult.ok();
    }

}
