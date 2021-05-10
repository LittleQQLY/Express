package com.QQLY.pojo;

import java.util.ArrayList;

public class GroupUser {
    private String flag;
    private TokenUser take;//֧����
    public ArrayList<TokenUser> p=new ArrayList<>();
    private boolean num;//�Ƿ���������Ȩ��
    public GroupUser(TokenUser x) {
        this.take=x;
        this.num=false;
    }
    public GroupUser(TokenUser x,ArrayList a) {
        this.take=x;
        this.p=a;
        this.num=true;
    }
    public GroupUser() {

    }
    public boolean isNum() {
        return num;
    }
    public void setNum(boolean num) {
        this.num = num;
    }
    public String getFlag() {
        return flag;
    }
    public void setP(ArrayList<TokenUser> p) {
        this.p = p;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public TokenUser getTake() {
        return take;
    }
    public void setTake(TokenUser take) {
        this.take = take;
    }
    public ArrayList<TokenUser> getP() {
        return p;
    }
    public void setP(TokenUser p) {
        this.p.add(p);
    }
}
