package com.QQLY.controller;

import com.QQLY.enums.OperatorFriendRequestTypeEnum;
import com.QQLY.enums.SearchFriendsStatusEnum;
import com.QQLY.mapper.UsersMapper;
import com.QQLY.pojo.Friends;
import com.QQLY.pojo.Users;
import com.QQLY.pojo.vo.FriendRequestVO;
import com.QQLY.pojo.vo.MyFriendsVO;
import com.QQLY.pojo.vo.UsersVO;
import com.QQLY.service.UserService;
import com.QQLY.utils.JSONResult;
import com.QQLY.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("u")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/registOrLogin")
    @ResponseBody
    public JSONResult registOrLogin(@RequestBody Users users) throws Exception {
        if(StringUtils.isBlank(users.getUsername())||StringUtils.isBlank(users.getPassword())){
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        //是否存在用户名
        boolean usernameIsExist=userService.queryUsernameIsExist(users.getUsername());
        Users userResult=null;
        if(usernameIsExist) {
            //判断账号密码是否正确
            userResult=userService.queryUserForLogin(users.getUsername(),
                    MD5Utils.getMD5Str(users.getPassword()));
            if(userResult==null) {
                return JSONResult.errorMsg("用户名或密码不正确");
            }
        }else{
            //密码加密，创建账号
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            userResult= userService.saveUser(users);
        }
        UsersVO userVO=new UsersVO();
        //beanutils工具类，可对bean进行操作
        BeanUtils.copyProperties(userResult,userVO);
        return JSONResult.ok(userVO);
    }

    @PostMapping("/search")
    @ResponseBody
    public JSONResult searchUser(String myUserId, String friendUsername) throws Exception {
        System.out.println(myUserId+"***"+friendUsername);
        // 0. 判断 myUserId friendUsername 不能为空
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("");
        }
        // 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
        // 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
        // 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            Users user = userService.queryUserInfoByUsername(friendUsername);
            UsersVO userVO = new UsersVO();
            BeanUtils.copyProperties(user, userVO);
            return JSONResult.ok(userVO);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }
    }

    //发送添加好友请求
    @PostMapping("/addFriendRequest")
    @ResponseBody
    public JSONResult addFriendRequest(String myUserId, String friendUsername) throws Exception {
        System.out.println(myUserId+"***"+friendUsername);
        // 0. 判断 myUserId friendUsername 不能为空
        if (StringUtils.isBlank(myUserId)
                || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("?");
        }
        // 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
        // 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
        // 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendsStatusEnum.SUCCESS.status) {
            userService.sendFriendRequest(myUserId,friendUsername);
        } else {
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }
        return JSONResult.ok();
    }

    //好友请求
    @PostMapping("/queryFriendRequests")
    @ResponseBody
    public JSONResult queryFriendRequest(String userId) throws Exception{
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        //查询用户接手到的朋友申请
        return JSONResult.ok(userService.queryFriendRequestList(userId));
    }

    //接收方通过或忽略朋友请求
    @PostMapping("/operFriendRequest")
    @ResponseBody
    public JSONResult operFriendRequest(String acceptUserId,String sendUserId,Integer operType){
        if (StringUtils.isBlank(acceptUserId) ||StringUtils.isBlank(sendUserId) ||operType==null) {
            return JSONResult.errorMsg("");
        }
        //若果操作类型没有对应的枚举值，抛空
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
            return JSONResult.errorMsg("");
        }
        if(operType==OperatorFriendRequestTypeEnum.IGNORE.type) {
            userService.deleteFriendRequest(sendUserId, acceptUserId);
            //忽略--删除请求
        }else if(operType==OperatorFriendRequestTypeEnum.PASS.type) {
            //接收
            userService.passFriendRequest(sendUserId, acceptUserId);
        }
        List<MyFriendsVO> myFirends=userService.queryMyFriends(acceptUserId);
        return JSONResult.ok(myFirends);
    }

    //查询好友
    @PostMapping("/myFriends")
    @ResponseBody
    public JSONResult myFriends(String userId) {
        System.out.println("myFriends");
        // 0. userId 判断不能为空
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        // 1. 数据库查询好友列表
        List<MyFriendsVO> myFirends = userService.queryMyFriends(userId);
        return JSONResult.ok(myFirends);
    }



}
