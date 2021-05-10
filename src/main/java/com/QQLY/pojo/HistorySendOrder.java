package com.QQLY.pojo;

import javax.persistence.*;

@Table(name = "history_send_order")
public class HistorySendOrder {
    @Id
    private String id;

    /**
     * 寄件人
     */
    private String payname;

    /**
     * 收件人
     */
    private String username;

    /**
     * 寄件地址
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
     * 获取寄件人
     *
     * @return payname - 寄件人
     */
    public String getPayname() {
        return payname;
    }

    /**
     * 设置寄件人
     *
     * @param payname 寄件人
     */
    public void setPayname(String payname) {
        this.payname = payname;
    }

    /**
     * 获取收件人
     *
     * @return username - 收件人
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置收件人
     *
     * @param username 收件人
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取寄件地址
     *
     * @return address - 寄件地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置寄件地址
     *
     * @param address 寄件地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
}