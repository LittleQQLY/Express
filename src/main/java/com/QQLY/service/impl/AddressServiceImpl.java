package com.QQLY.service.impl;

import com.QQLY.mapper.AddressMapper;
import com.QQLY.pojo.Address;
import com.QQLY.pojo.FriendRequest;
import com.QQLY.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void addAddress(String username, String address) {
        Address as=new Address();
        as.setAddress(address);
        as.setUsername(username);
        addressMapper.insert(as);
    }

    @Override
    public void deleteAddress(String username, String address) {
        Example fre = new Example(Address.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("username", username);
        frc.andEqualTo("address", address);
        addressMapper.deleteByExample(frc);
    }

    @Override
    public void updateAddress(String username, String address) {
        Address as=new Address();
        as.setAddress(address);
        as.setUsername(username);
        addressMapper.updateByPrimaryKey(as);
    }

    @Override
    public List<Address> selectAll(String username) {
        Example fre = new Example(Address.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("username", username);
        List<Address> list=addressMapper.selectByExample(frc);
        return list;
    }
}
