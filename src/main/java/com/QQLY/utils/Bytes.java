package com.QQLY.utils;

import java.security.MessageDigest;
import java.security.Signature;


import org.apache.commons.codec.binary.Base64;

public class Bytes {


	//byte转String
	public static String byteArrayToHexStr(byte[] byteArray) {
		if (byteArray == null){
			return null;
		}
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[byteArray.length * 2];
		for (int j = 0; j < byteArray.length; j++) {
			int v = byteArray[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	//string转byte
	public static byte[] hexStrToByteArray(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return new byte[0];
		}
		byte[] byteArray = new byte[str.length() / 2];
		for (int i = 0; i < byteArray.length; i++){
			String subStr = str.substring(2 * i, 2 * i + 2);
			byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
		}
		return byteArray;
	}
}
