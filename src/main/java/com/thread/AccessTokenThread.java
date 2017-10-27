package com.thread;

import com.adcc.utility.log.Log;
import com.model.accesstoken.AccessToken;
import com.util.WeixinUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时获取微信access_token的线程
 * 在WechatMpDemoApplication中注解@EnableScheduling，在程序启动时就开启定时任务。
 * 每7200秒执行一次
 */
@Component
public class AccessTokenThread {

    // 第三方用户唯一凭证
    public static String appid = "wx83dad0407e617c02";

    // 第三方用户唯一凭证密钥
    public static String appsecret = "e2c222228707f8146b180c8335992bb1";
    // 第三方用户唯一凭证
    public static AccessToken accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200秒执行一次
    public void gettoken(){
        accessToken= WeixinUtil.getAccessToken(appid,appsecret);
        if(null!=accessToken){
            Log.info("获取成功，accessToken:"+accessToken.getAccess_token());
        }else {
            Log.error("获取token失败");
        }
    }
}
