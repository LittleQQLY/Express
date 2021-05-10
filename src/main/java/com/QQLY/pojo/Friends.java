package com.QQLY.pojo;

import javax.persistence.*;

public class Friends {
    @Id
    private String id;

    /**
     * 我的id
     */
    @Column(name = "my_user_id")
    private String myUserId;

    /**
     * 好友id
     */
    @Column(name = "my_friend_user_id")
    private String myFriendUserId;

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
     * 获取我的id
     *
     * @return my_user_id - 我的id
     */
    public String getMyUserId() {
        return myUserId;
    }

    /**
     * 设置我的id
     *
     * @param myUserId 我的id
     */
    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    /**
     * 获取好友id
     *
     * @return my_friend_user_id - 好友id
     */
    public String getMyFriendUserId() {
        return myFriendUserId;
    }

    /**
     * 设置好友id
     *
     * @param myFriendUserId 好友id
     */
    public void setMyFriendUserId(String myFriendUserId) {
        this.myFriendUserId = myFriendUserId;
    }
}