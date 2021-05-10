package com.QQLY.service;

import com.QQLY.pojo.Users;
import com.QQLY.pojo.vo.FriendRequestVO;
import com.QQLY.pojo.vo.MyFriendsVO;

import java.util.List;

public interface UserService {

    //判断用户名是否存在
    public  boolean queryUsernameIsExist(String username);
    //查询用户是否存在
    public Users queryUserForLogin(String username,String pwd);
    //用户注册
    public Users saveUser(Users user);
    //修改用户
    public Users updateUserInfo(Users user);
    //搜索朋友的前置条件
    public Integer preconditionSearchFriends(String myUserId, String friendUsername);
    //根据用户名查询用户对象
    public Users queryUserInfoByUsername(String username);
    //添加好友请求记录，保存到数据库
    public void sendFriendRequest(String myUserId,String friendUsername);
    //查询好友请求
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);
    //删除好友记录
    public void deleteFriendRequest(String sendUserId,String acceptUserId);
    //通过好友请求
    //保存好友、逆向保存好友、删除请求记录
    public void passFriendRequest(String sendUserId,String acceptUserId);
    //查询好友列表
    public List<MyFriendsVO> queryMyFriends(String userId);
}
