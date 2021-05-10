package com.QQLY.service;

import com.QQLY.pojo.CourierDetails;
import com.QQLY.pojo.CourierList;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface CourierService {

    //查询所有快递
    public List<CourierList> selectAll();

    //查询个人快递
    public List<CourierList> selectByUsername(String username);

    //查询快递详情
    public CourierDetails selectById(String id);

    //录入快递
    public boolean saveCourier(CourierDetails courierDetails);

    //更新取件信息
    public void updateCourier(String id,String username, Date data);

    //用户获取取件二维码
    public String getQr(String courierId);

    //删除快递
    public void deleteCourier(String num);

}
