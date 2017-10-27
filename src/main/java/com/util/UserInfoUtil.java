package com.util;

/**
 * Created by ZP on 2017/10/26.
 */
public class UserInfoUtil {

    //获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";
    //替换字符串
    public static String getCode(String APPID, String REDIRECT_URI,String SCOPE) {
        return String.format(Get_Code,APPID,REDIRECT_URI,SCOPE);
    }

    //获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    //替换字符串
    public static String getWebAccess(String APPID, String SECRET,String CODE) {
        return String.format(Web_access_tokenhttps, APPID, SECRET,CODE);
    }

    //拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    //替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }

    public static void main(String[] args) {
        String  REDIRECT_URI = "http://119.29.110.51:80/url";
        /**
         * 当scope=snsapi_userinfo的时候，弹出询问授权页面，用户同意，才可进一步获取用户基本信息
         * 当scope=snsapi_base的时候,不会出现询问页面，但只能获取用户openid(用户的唯一标识)
         */
        String SCOPE = "snsapi_userinfo";
        //appId
        String appId = "wx83dad0407e617c02";

        String getCodeUrl = getCode(appId, REDIRECT_URI, SCOPE);
        System.out.println("getCodeUrl:"+getCodeUrl);
        // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx83dad0407e617c02&redirect_uri=http://119.29.110.51:80/url&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect
    }
}
