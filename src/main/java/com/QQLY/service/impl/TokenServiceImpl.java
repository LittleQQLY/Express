package com.QQLY.service.impl;


import com.QQLY.mapper.SendOrderMapper;
import com.QQLY.mapper.TokenMapper;
import com.QQLY.pojo.GroupUser;
import com.QQLY.pojo.TokenUser;
import com.QQLY.service.TokenService;
import com.QQLY.utils.Bytes;
import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import io.everitoken.sdk.java.*;
import io.everitoken.sdk.java.abi.DestroyTokenAction;
import io.everitoken.sdk.java.abi.IssueTokenAction;
import io.everitoken.sdk.java.apiResource.Info;
import io.everitoken.sdk.java.dto.NodeInfo;
import io.everitoken.sdk.java.dto.TokenDomain;
import io.everitoken.sdk.java.dto.TransactionData;
import io.everitoken.sdk.java.example.ApiExample;
import io.everitoken.sdk.java.exceptions.ApiResponseException;
import io.everitoken.sdk.java.param.MainNetNetParams;
import io.everitoken.sdk.java.param.NetParams;
import io.everitoken.sdk.java.param.PublicKeysParams;
import io.everitoken.sdk.java.param.RequestParams;
import io.everitoken.sdk.java.provider.KeyProvider;
import io.everitoken.sdk.java.provider.KeyProviderInterface;
import io.everitoken.sdk.java.provider.SignProvider;
import io.everitoken.sdk.java.provider.SignProviderInterface;
import io.everitoken.sdk.java.service.TransactionConfiguration;
import io.everitoken.sdk.java.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.QQLY.mapper.TokenMapper;

import java.io.IOException;

import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {


    TokenMapper tokenMapper=new TokenMapper();

    @Value("${com.priveteKey}")
    private String privateKey;

    public String destroyTokenly(TokenUser x, String name) {
        NetParams netParams = new MainNetNetParams(NetParams.NET.MAINNET1);
        DestroyTokenAction destroyTokenAction = DestroyTokenAction.of("Send", name);
        try {
            TransactionService transactionService = TransactionService.of((netParams));
            TransactionConfiguration trxConfig = new TransactionConfiguration(1000000, PublicKey.of(x.getPublicKey().toString()), (KeyProviderInterface)KeyProvider.of(x.getPrivateKey().toWif()));
            TransactionData txData = transactionService.push(trxConfig, Arrays.asList(new DestroyTokenAction[] { destroyTokenAction }));
            return txData.getTrxId();
        }catch (ApiResponseException ex) {
            System.out.println(ex.getRaw());
        }
        return null;
    }

    @Override
    public String destroyTokenly(TokenUser x, String doname, String name) {
        return null;
    }

    @Override
    public String findTokenly(TokenUser x) throws ApiResponseException {
        ApiExample a=new ApiExample();
        NetParams netParams = new MainNetNetParams(NetParams.NET.MAINNET1);
        PublicKeysParams publicKeysParams = new PublicKeysParams(new String[] { x.publicKey.toString() });
        List<TokenDomain> res = (new Api((netParams))).getOwnedTokens(publicKeysParams);
        return JSON.toJSONString(res);
    }

    @Override
    public String getTokenPhoto(TokenUser x, String doname, String name) throws WriterException {
        NetParams netParams = new MainNetNetParams(NetParams.NET.MAINNET1);
        EvtLink evtLink = new EvtLink((netParams));
        EvtLink.EveriPassParam everiPassParam = new EvtLink.EveriPassParam(true,doname,name);
        String passText = evtLink.getEvtLinkForEveriPass(everiPassParam,(SignProviderInterface) SignProvider.of((KeyProviderInterface) KeyProvider.of(x.getPrivateKey().toWif())));
        try {
            return Utils.getQrImageDataUri(passText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Signature getDigestly(String a, TokenUser x) {
        byte[] digest=new byte[32];
        digest= Bytes.hexStrToByteArray(a);
        return tokenMapper.getsignaturely(digest,x);
    }

    @Override
    public String Multiplely()  {
        byte[] digest = new byte[32];
        new Random(0).nextBytes(digest);
        return Bytes.byteArrayToHexStr(digest);
    }

    @Override
    //管理员万能二维码
    public String getAdminPhoto() {

        return null;
    }

    @Override
    public boolean signaturely(Signature a, byte[] digest, TokenUser x) {
        if(Signature.verify(digest,a,PublicKey.of(x.getPublicKey().toString()))==true&&Signature.recoverPublicKey(digest,a)==x.getPublicKey()) {
            return true;
        }
        return false;
    }

    @Override
    public String createTokenly(
            GroupUser x, String name, String doname) {//ӵ����,֤ͨ��,����
        KeyProvider keyProvider = KeyProvider.of(x.getTake().getPrivateKey().toWif());//֧����
        try {
            NodeInfo nodeInfo = (new Info()).request(RequestParams.of(new MainNetNetParams(NetParams.NET.MAINNET1)));
        } catch (ApiResponseException e1) {
            return "连接失败";
        }
        TransactionConfiguration trxConfig = new TransactionConfiguration(1000000, PublicKey.of(x.getTake().getPublicKey().toString()), keyProvider);
        TransactionData txData;
        if(x.isNum()==false) {//˽��
            try {
                txData = tokenMapper.getTransNodely().push(trxConfig, Arrays.asList(new IssueTokenAction[] { tokenMapper.getIssuely(x,name,doname,false) }));
            } catch (ApiResponseException e) {
                return "数据提交失败";
            }
            return txData.getTrxId();
        }else {
            try {
                txData = tokenMapper.getTransNodely().push(trxConfig, Arrays.asList(new IssueTokenAction[] { tokenMapper.getIssuely(x,name,doname,true) }));
            } catch (ApiResponseException e) {
                return "数据提交失败";
            }
            return txData.getTrxId();
        }
    }
}
