package com.QQLY.service;

import com.QQLY.pojo.HistorySendOrder;
import com.QQLY.pojo.SendOrder;

import java.util.List;

public interface OrderService {

    //保存寄件订单
    public void saveOrder(SendOrder sendOrder);

    //历史寄件
    public List<HistorySendOrder> selectAllOrder();

    //寄件详情
    public SendOrder selectOrderById(String orderId);

    //删除已完成寄件
    public boolean deleteOrder(String orderId);

    //寄件状态变动
    public boolean updateOrder(String orderId,String num);

    public List<SendOrder> selectOrderByPayname(String payname);
}
