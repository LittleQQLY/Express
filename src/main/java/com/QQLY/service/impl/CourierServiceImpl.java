package com.QQLY.service.impl;

import com.QQLY.mapper.CourierDetailsMapper;
import com.QQLY.mapper.CourierListMapper;
import com.QQLY.mapper.UsersMapper;
import com.QQLY.pojo.CourierDetails;
import com.QQLY.pojo.CourierList;
import com.QQLY.pojo.SendOrder;
import com.QQLY.pojo.Users;
import com.QQLY.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;


//快递模块暂且不动，需加evt交互
@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierListMapper courierListMapper;

    @Autowired
    private CourierDetailsMapper courierDetailsMapper;


//    查询所有快递
    @Override
    public List<CourierList> selectAll() {
        List<CourierList> lists=courierListMapper.selectAll();
        return lists;
    }

    @Override
    public List<CourierList> selectByUsername(String username){
        Example fre = new Example(CourierList.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("username", username);
        List<CourierList> lists=courierListMapper.selectByExample(frc);
        return lists;
    }

    @Override
    public CourierDetails selectById(String id) {
        CourierDetails courierDetails=courierDetailsMapper.selectByPrimaryKey(id);
        return courierDetails;
    }

    @Override
    public boolean saveCourier(CourierDetails courierDetails) {
        CourierList live=courierListMapper.selectByPrimaryKey(courierDetails.getId());
        if(live!=null){
            return false;
        }
        return true;
    }

    //取走，更新
    @Override
    public void updateCourier(String id, String username, Date data) {
        CourierDetails courierDetails=courierDetailsMapper.selectByPrimaryKey(id);
        courierDetails.setTime2(data);
        CourierList courierList=courierListMapper.selectByPrimaryKey(id);
        courierList.setTake(username);
        courierDetailsMapper.updateByPrimaryKey(courierDetails);
    }

    @Override
    public String getQr(String courierId) {
        return null;
    }

    @Override
    public void deleteCourier(String num) {
        Example fre = new Example(CourierList.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("num", num);
        CourierDetails courierDetails=courierDetailsMapper.selectOneByExample(frc);
        courierListMapper.deleteByPrimaryKey(courierDetails.getId());
        courierDetailsMapper.deleteByPrimaryKey(courierDetails.getId());
    }
}
