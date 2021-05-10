package com.QQLY.pojo;

import javax.persistence.*;

@Table(name = "courier_list")
public class CourierList {
    @Id
    private String id;

    /**
     * 收件人
     */
    private String name;

    /**
     * 快递单号
     */
    private String domain;

    /**
     * 拥有者
     */
    private String own;

    /**
     * 是否加密
     */
    private String value;

    /**
     * 取件人
     */
    private String take;

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
     * @return name - 收件人
     */
    public String getName() {
        return name;
    }

    /**
     * 设置收件人
     *
     * @param name 收件人
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取快递单号
     *
     * @return domain - 快递单号
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置快递单号
     *
     * @param domain 快递单号
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取拥有者
     *
     * @return own - 拥有者
     */
    public String getOwn() {
        return own;
    }

    /**
     * 设置拥有者
     *
     * @param own 拥有者
     */
    public void setOwn(String own) {
        this.own = own;
    }

    /**
     * 获取是否加密
     *
     * @return value - 是否加密
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置是否加密
     *
     * @param value 是否加密
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取取件人
     *
     * @return take - 取件人
     */
    public String getTake() {
        return take;
    }

    /**
     * 设置取件人
     *
     * @param take 取件人
     */
    public void setTake(String take) {
        this.take = take;
    }
}