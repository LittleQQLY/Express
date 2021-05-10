package com.QQLY.pojo;

import javax.persistence.*;

@Table(name = "friend_request")
public class FriendRequest {
    @Id
    private String id;

    /**
     * 好友请求发送人
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 好友请求接收人
     */
    @Column(name = "accept_user_id")
    private String acceptUserId;

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
     * 获取好友请求发送人
     *
     * @return send_user_id - 好友请求发送人
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置好友请求发送人
     *
     * @param sendUserId 好友请求发送人
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取好友请求接收人
     *
     * @return accept_user_id - 好友请求接收人
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * 设置好友请求接收人
     *
     * @param acceptUserId 好友请求接收人
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }
}