package com.QQLY.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "send_order")
public class SendOrder {
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
     * 地址
     */
    private String address;

    /**
     * 快递信息
     */
    private String msg;

    /**
     * 收件人电话
     */
    private String phone;

    /**
     * 寄件状态
     */
    private String status;

    /**
     * 录入/处理时间
     */
    private Date time;

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
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取快递信息
     *
     * @return msg - 快递信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置快递信息
     *
     * @param msg 快递信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取收件人电话
     *
     * @return phone - 收件人电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置收件人电话
     *
     * @param phone 收件人电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取寄件状态
     *
     * @return status - 寄件状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置寄件状态
     *
     * @param status 寄件状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取录入/处理时间
     *
     * @return time - 录入/处理时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置录入/处理时间
     *
     * @param time 录入/处理时间
     */
    public void setTime(Date time) {
        this.time = time;
    }
}