package com.QQLY.utils;

import com.QQLY.service.impl.AuthService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

public class General {
    public String IdentifyImg(String filePath){
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";
        try {
            InputStream in;
            byte[] data = null;
            // 读取图片字节数组
            try {
                in = new FileInputStream(filePath);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String imgStr = Base64Util.encode(data);
//            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            String recogniseParams = "templateSign=19edaa0d264ee6e90344e44b4dcb7118&image=" + URLEncoder.encode(imgStr, "UTF-8");
            //线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();//#####调用鉴权接口获取的token#####
            //开始搭建post请求
            return  HttpUtil.post(otherHost, accessToken, recogniseParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
