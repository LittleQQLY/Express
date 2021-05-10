package com.QQLY.service.impl;

import com.QQLY.enums.SearchFriendsStatusEnum;
import com.QQLY.mapper.FriendRequestMapper;
import com.QQLY.mapper.FriendsMapper;
import com.QQLY.mapper.UsersMapper;
import com.QQLY.pojo.FriendRequest;
import com.QQLY.pojo.Friends;
import com.QQLY.pojo.Users;
import com.QQLY.pojo.vo.FriendRequestVO;
import com.QQLY.pojo.vo.MyFriendsVO;
import com.QQLY.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import org.n3r.idworker.Sid;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Autowired
    private Sid sid;

    //判断用户名是否存在
    public boolean queryUsernameIsExist(String username) {
        Users users=new Users();
        users.setUsername(username);
        Users result=userMapper.selectOne(users);
        return result!=null?true:false;
    }

    //判断用户是否存在
    public Users queryUserForLogin(String username, String password) {
        Example userExample=new Example(Users.class);
        Example.Criteria criteria=userExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        Users result=userMapper.selectOneByExample(userExample);
        return result;
    }

    public Users saveUser(Users user) {
        userMapper.insert(user);
        return user;
    }

    //注册
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users updateUserInfo(Users user) {
        userMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    //根据ID查询
    private Users queryUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    //搜索好友的先决条件
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {
        Users user = queryUserInfoByUsername(friendUsername);
        // 1. 搜索的用户如果不存在，返回[无此用户]
        if (user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        // 2. 搜索账号是你自己，返回[不能添加自己]
        if (user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        // 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        Example mfe = new Example(Friends.class);
        Example.Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("myUserId", myUserId);
        mfc.andEqualTo("myFriendUserId", user.getId());
        Friends myFriendsRel = friendsMapper.selectOneByExample(mfe);
        if (myFriendsRel != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    //根据用户名查询用户
    public Users queryUserInfoByUsername(String username) {
        Example ue = new Example(Users.class);
        Example.Criteria uc = ue.createCriteria();
        uc.andEqualTo("username", username);
        return userMapper.selectOneByExample(ue);
    }

    //添加好友请求
    public void sendFriendRequest(String myUserId, String friendUsername) {
        Users friend=queryUserInfoByUsername(friendUsername);
        //查询发送好友请求记录表
        Example fre = new Example(FriendRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendRequest friendsRequest=friendRequestMapper.selectOneByExample(fre);
        if(friendsRequest==null) {
            //如果不是好友且好友记录未添加
//            String requestId=sid.nextShort();
            FriendRequest request=new FriendRequest();
//            request.setId(requestId);
            request.setSendUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            friendRequestMapper.insert(request);
        }
    }

    //查询好友请求
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return userMapper.queryFriendRequestList(acceptUserId);
    }

    //删除好友请求
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", sendUserId);
        frc.andEqualTo("acceptUserId", acceptUserId);
        friendRequestMapper.deleteByExample(fre);
    }

    //通过好友请求
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);
    }

    //保存好友
    @Transactional(propagation=Propagation.REQUIRED)  //事务
    public void saveFriends(String sendUserId, String acceptUserId) {
        Friends myFriends=new Friends();
        String recordId=sid.nextShort();
        myFriends.setId(recordId);
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        friendsMapper.insert(myFriends);
    }

    //查询我的好友
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFirends = userMapper.queryMyFriends(userId);
        return myFirends;
    }
}
