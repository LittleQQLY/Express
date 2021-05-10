package com.QQLY.service;

import com.QQLY.pojo.Address;

import java.util.List;

public interface AddressService {

    //增
    public void addAddress(String username ,String address);
    //删
    public void deleteAddress(String username ,String address);
    //改
    public  void updateAddress(String username,String address);
    //查
    public List<Address> selectAll(String username);
}
