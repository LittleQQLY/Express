package com.QQLY.mapper;


import com.QQLY.pojo.GroupUser;
import com.QQLY.pojo.TokenUser;


import io.everitoken.sdk.java.Address;
import io.everitoken.sdk.java.PublicKey;
import io.everitoken.sdk.java.Signature;
import io.everitoken.sdk.java.abi.Evt2PevtAction;
import io.everitoken.sdk.java.abi.IssueTokenAction;
import io.everitoken.sdk.java.abi.NewDomainAction;
import io.everitoken.sdk.java.abi.NewSuspendAction;
import io.everitoken.sdk.java.dto.Transaction;
import io.everitoken.sdk.java.dto.TransactionData;
import io.everitoken.sdk.java.exceptions.ApiResponseException;
import io.everitoken.sdk.java.param.MainNetNetParams;
import io.everitoken.sdk.java.param.NetParams;
import io.everitoken.sdk.java.provider.KeyProvider;
import io.everitoken.sdk.java.provider.KeyProviderInterface;
import io.everitoken.sdk.java.service.TransactionConfiguration;
import io.everitoken.sdk.java.service.TransactionService;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TokenMapper {

    public TransactionService getTransNodely() {
        NetParams netParams = new MainNetNetParams(NetParams.NET.MAINNET1);
        TransactionService transactionService = TransactionService.of((netParams));
        return transactionService;
    }

    public String getDomainly(TokenUser x,String name) throws ApiResponseException {//������ ,�����ߣ�����
        GroupUser a=new GroupUser(x);a.setFlag(name);
        TransactionConfiguration trxConfig = new TransactionConfiguration(1000000, PublicKey.of(x.getPublicKey().toString()), (KeyProviderInterface) KeyProvider.of(x.getPrivateKey().toWif()));
        TransactionData txData = getTransNodely().push(trxConfig, Arrays.asList(new NewDomainAction[] { domainly(a) }));
        return txData.getTrxId();
    }

    public IssueTokenAction getIssuely(GroupUser x, String name, String doname, boolean flag) {
        IssueTokenAction issueTokenAction;
        if(flag==false) {
            issueTokenAction = IssueTokenAction.of(doname, Arrays.asList(name),
                    Collections.singletonList(Address.of(x.getTake().getPublicKey())));
        }else {
            ArrayList<Address> p=new ArrayList<>();
            for(int i=0;i<x.getP().size();++i) {
                p.add(Address.of(x.getP().get(i).getPublicKey()));
            }
            issueTokenAction = IssueTokenAction.of(doname, Arrays.asList(name),p);
        }
        return issueTokenAction;
    }

    public NewDomainAction domainly(GroupUser x) {
        String data;
        if(x.isNum()==false) {//
            data = "{\"name\":\""
                    + x.getFlag()
                    + "\",\"creator\":\""
                    + x.getTake().getPublicKey()
                    + "\",\"issue\":{\"name\":\"issue\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[A] "
                    + x.getTake().getPublicKey()
                    + "\",\"weight\":1}]},\"transfer\":{\"name\":\"transfer\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[G] .OWNER\",\"weight\":1}]},\"manage\":{\"name\":\"manage\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[A] "
                    + x.getTake().getPublicKey()
                    + "\",\"weight\":1}]}}";
        }else {
            data = "{\"name\":\""
                    + x.getFlag()
                    + "\",\"creator\":\""
                    + x.getTake().getPublicKey()
                    + "\",\"issue\":{\"name\":\"issue\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[A] "
                    + x.getTake().getPublicKey()
                    + "\",\"weight\":1}]},\"transfer\":{\"name\":\"transfer\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[G] .OWNER\",\"weight\":1}]},\"manage\":{\"name\":\"manage\",\"threshold\":1,\"authorizers\":[{\"ref\":\"[A] "
                    + x.getTake().getPublicKey()
                    + "\",\"weight\":1}]}}";
        }
        JSONObject json = new JSONObject(data);
        NewDomainAction newDomainAction = NewDomainAction.ofRaw(json.getString("name"), json.getString("creator"), json.getJSONObject("issue"), json.getJSONObject("transfer"), json.getJSONObject("manage"));
        return newDomainAction;
    }

    public Signature getsignaturely(byte[] digest, TokenUser x) {
        NetParams netParams = new MainNetNetParams(NetParams.NET.MAINNET1);
        Evt2PevtAction evt2PevtAction = Evt2PevtAction.of("0.30000 S#1", "EVT6Qz3wuRjyN6gaU3P3XRxpnEZnM4oPxortemaWDwFRvsv2FxgND", "EVT8aNw4NTvjBL1XR6hgy4zcA9jzh1JLjMuAw85mSbW68vYzw2f9H", "test java");
        TransactionService transactionService = TransactionService.of((netParams));
        String push="a";
        KeyProvider keyProvider = KeyProvider.of(x.getPrivateKey().toWif());
        x.setPublicKey(x.getPrivateKey().toPublicKey().toString());
        TransactionConfiguration trxConfig = new TransactionConfiguration(1000000, PublicKey.of(x.getPublicKey().toString()), (KeyProviderInterface)KeyProvider.of(x.getPrivateKey().toWif()));
        try {
            Transaction tx = transactionService.buildRawTransaction(trxConfig, Arrays.asList(new Evt2PevtAction[] { evt2PevtAction }));
            NewSuspendAction action = NewSuspendAction.of("testProposal13", x.getPublicKey().toString(), tx);
            push = transactionService.push1(trxConfig, Arrays.asList(new NewSuspendAction[] { action }),digest);
        } catch (ApiResponseException ex) {
            System.out.println("��ȡǩ������");
        }
        String sign=(String) push.subSequence(1, push.length()-1);
        System.out.println(sign);
        return Signature.of(sign);
    }
}
