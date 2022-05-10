package com.qdc.demoeurekaconsumer1.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//用于获取access token
//类名自定义的
public class HttpClientWithBasicAuth {
//    传入用户名和密码      postman的Authorization中的参数
    private String getHeader(String Username,String Password){
//        auth由两部分组成：Username和Password组成
//        冒号是通用格式
        String auth=Username+":"+Password;
//        使用Base64将auth进行加密      auth转化为二进制，用US-ASCII编码
        byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
//        将encodeAuth转化为字符串
//        Basic后面有一个空格
        String authHeader="Basic "+new String(encodeAuth);
        return authHeader;
    }
//     postman的send按钮
    public String send(String url, String UserName, String Password, Map<String,String> params){
//        用post方法提交
        HttpPost post=new HttpPost(url);
//        用get方法提交
//        HttpGet get=new HttpGet(url);
        CloseableHttpClient client= HttpClients.createDefault();
//        组织请求参数，在获取token时参数为grant_type和scope
        List<NameValuePair> paramList=new ArrayList<>();
//        遍历params集合
        if (params!=null && params.size()>0){
//            获得所有键集合
            Set<String> keySet=params.keySet();
            for (String key:keySet){
                paramList.add(new BasicNameValuePair(key,params.get(key)));
            }
        }
        try {
//            设置要提交的数据
            post.setEntity(new UrlEncodedFormEntity(paramList,"utf-8"));
        }catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
        }
//        调用getHeader(UserName,Password)方法，命名为Authorization
            post.addHeader("Authorization",getHeader(UserName,Password));
//        post.setEntity(myEntity);    //设置请求体
//        响应内容
        String responseContent=null;
        CloseableHttpResponse response=null;
        try {
//            执行post请求，生成oauthtoken，调用auth_server中的configure方法
            response=client.execute(post);
//            拿到状态码
            int status_code=response.getStatusLine().getStatusCode();
//            拿到响应体
            System.out.println("状态码："+status_code);
         if(response.getStatusLine().getStatusCode()==200){
             HttpEntity entity=response.getEntity();
             responseContent= EntityUtils.toString(entity,"UTF-8");
         }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (client!=null){
                    client.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return responseContent;
    }

}
