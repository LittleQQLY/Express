package com.QQLY.pojo;

import javax.persistence.*;

public class Users {
    @Id
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户二维码
     */
    private String qrcode;

    /**
     * 用户身份
     */
    private String power;

    /**
     * 用户头像
     */
    private String picture;

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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户二维码
     *
     * @return qrcode - 用户二维码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置用户二维码
     *
     * @param qrcode 用户二维码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    /**
     * 获取用户身份
     *
     * @return power - 用户身份
     */
    public String getPower() {
        return power;
    }

    /**
     * 设置用户身份
     *
     * @param power 用户身份
     */
    public void setPower(String power) {
        this.power = power;
    }

    /**
     * 获取用户头像
     *
     * @return picture - 用户头像
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置用户头像
     *
     * @param picture 用户头像
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
}