package com.QQLY.service.impl;

import com.QQLY.mapper.HistorySendOrderMapper;
import com.QQLY.mapper.SendOrderMapper;
import com.QQLY.pojo.FriendRequest;
import com.QQLY.pojo.HistorySendOrder;
import com.QQLY.pojo.SendOrder;
import com.QQLY.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.jar.JarEntry;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SendOrderMapper sendOrderMapper;

    @Autowired
    private HistorySendOrderMapper historySendOrderMapper;


    //两个表同时存储
    public void saveOrder(SendOrder sendOrder) {
        sendOrderMapper.insert(sendOrder);
        HistorySendOrder historySendOrder=new HistorySendOrder();
        historySendOrder.setId(sendOrder.getId());
        historySendOrder.setAddress(sendOrder.getAddress());
        historySendOrder.setPayname(sendOrder.getPayname());
        historySendOrder.setUsername(sendOrder.getUsername());
        historySendOrderMapper.insert(historySendOrder);
    }



    //订单列表从历史表单中获取
    public List<HistorySendOrder> selectAllOrder() {
        List<HistorySendOrder> orders=historySendOrderMapper.selectAll();
        return orders;
    }

    //从列表跳转详细信息，查询详细表
    public SendOrder selectOrderById(String orderId) {
        SendOrder sendOrder=sendOrderMapper.selectByPrimaryKey(orderId);
        return sendOrder;
    }

    //删除过期订单
    public boolean deleteOrder(String orderId) {
        if(historySendOrderMapper.selectByPrimaryKey(orderId)==null) return false;
        sendOrderMapper.deleteByPrimaryKey(orderId);
        historySendOrderMapper.deleteByPrimaryKey(orderId);
        return true;
    }

    //更改寄件状态码
    public boolean updateOrder(String orderId,String num) {
        SendOrder sendOrder=sendOrderMapper.selectByPrimaryKey(orderId);
        sendOrder.setStatus(num);
        sendOrderMapper.updateByPrimaryKey(sendOrder);
        return true;
    }

    //查询用户个人寄件
    public List<SendOrder> selectOrderByPayname(String payname) {
        Example fre = new Example(SendOrder.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("payname", payname);
        List<SendOrder> list=sendOrderMapper.selectByExample(fre);
        return list;
    }
}
