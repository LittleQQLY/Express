package com.QQLY.pojo;

import io.everitoken.sdk.java.PrivateKey;
import io.everitoken.sdk.java.PublicKey;

public class TokenUser {

	private String name;//用户名
	private PrivateKey privateKey;//私钥
	public PublicKey publicKey;//公钥
	public int weight;//权值
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String s) {
		this.privateKey=PrivateKey.of(s);
	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String s) {
		this.publicKey = PublicKey.of(s);
	}
	
}
