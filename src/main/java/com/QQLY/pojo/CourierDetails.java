package com.QQLY.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "courier_details")
public class CourierDetails {
    @Id
    private String id;

    /**
     * 收件人
     */
    private String username;

    /**
     * 快递单号
     */
    private String num;

    /**
     * 阈值
     */
    private String value;

    /**
     * 录入时间
     */
    private Date time1;

    /**
     * 取件时间
     */
    private Date time2;

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
     * 获取快递单号
     *
     * @return num - 快递单号
     */
    public String getNum() {
        return num;
    }

    /**
     * 设置快递单号
     *
     * @param num 快递单号
     */
    public void setNum(String num) {
        this.num = num;
    }

    /**
     * 获取阈值
     *
     * @return value - 阈值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置阈值
     *
     * @param value 阈值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取录入时间
     *
     * @return time1 - 录入时间
     */
    public Date getTime1() {
        return time1;
    }

    /**
     * 设置录入时间
     *
     * @param time1 录入时间
     */
    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    /**
     * 获取取件时间
     *
     * @return time2 - 取件时间
     */
    public Date getTime2() {
        return time2;
    }

    /**
     * 设置取件时间
     *
     * @param time2 取件时间
     */
    public void setTime2(Date time2) {
        this.time2 = time2;
    }
}