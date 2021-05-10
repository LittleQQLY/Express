package com.QQLY.service;



import com.QQLY.pojo.GroupUser;
import com.QQLY.pojo.TokenUser;
import com.google.zxing.WriterException;
import io.everitoken.sdk.java.Signature;
import io.everitoken.sdk.java.exceptions.ApiResponseException;

import java.io.UnsupportedEncodingException;

import java.util.Random;

public interface TokenService {

    //销毁
    public String destroyTokenly(TokenUser x, String doname, String name);
    //查询
    public String findTokenly(TokenUser x) throws ApiResponseException;
    //二维码
    public String getTokenPhoto(TokenUser x, String doname, String name) throws WriterException;
    //签名
    public Signature getDigestly(String a, TokenUser x);
    //判签名
    public boolean signaturely(Signature a, byte[] digest, TokenUser x);
    //创建
    public String createTokenly(GroupUser x, String name, String doname);
    //随机生成摘要
    public String Multiplely();
    public String getAdminPhoto();

}
