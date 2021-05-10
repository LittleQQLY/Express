package com.QQLY.pojo;

import javax.persistence.*;

@Table(name = "box_stutas")
public class BoxStutas {
    @Id
    private String id;

    /**
     * 设备名
     */
    private String name;

    /**
     * 设备地址
     */
    private String address;

    /**
     * 使用状态
     */
    private String status;

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
     * 获取设备名
     *
     * @return name - 设备名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备名
     *
     * @param name 设备名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取设备地址
     *
     * @return address - 设备地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置设备地址
     *
     * @param address 设备地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取使用状态
     *
     * @return status - 使用状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置使用状态
     *
     * @param status 使用状态
     */
    public void setStatus(String status) {
        this.status = status;
    }
}