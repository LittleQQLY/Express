package com.QQLY.pojo;

import javax.persistence.*;

public class Address {
    @Id
    private String id;

    private String username;

    /**
     * 地址簿地址
     */
    private String address;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取地址簿地址
     *
     * @return address - 地址簿地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址簿地址
     *
     * @param address 地址簿地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
}