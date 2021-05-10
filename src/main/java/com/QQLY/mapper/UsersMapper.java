package com.QQLY.mapper;

import com.QQLY.pojo.Users;
import com.QQLY.pojo.vo.FriendRequestVO;
import com.QQLY.pojo.vo.MyFriendsVO;
import com.QQLY.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UsersMapper extends MyMapper<Users> {
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);
}