package com.controller;

import com.adcc.utility.log.Log;
import com.util.Constant;
import com.util.HttpsUtil;
import com.util.UserInfoUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZP on 2017/10/26.
 */
@RestController
public class RedirectController {

    /**
     * 微信网页授权流程:
     * 1. 用户同意授权,获取 code
     * 2. 通过 code 换取网页授权 access_token
     * 3. 使用获取到的 access_token 和 openid 拉取用户信息
     * @param code  用户同意授权后,获取到的code
     * @param state 重定向状态参数
     * @return
     */
    @GetMapping("/url")
    public String wecahtLogin(@RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state") String state) {

        // 1. 用户同意授权,获取code
        Log.info("收到微信重定向跳转.");
        Log.info("用户同意授权,获取code:{"+code+"} , state:{"+state+"}");

        // 2. 通过code换取网页授权access_token
        if (code != null || !(code.equals(""))) {

            String APPID = Constant.appId;
            String SECRET = Constant.appSecret;
            String CODE = code;
            String WebAccessToken = "";
            String openId = "";
            String nickName,sex,openid = "";
            String REDIRECT_URI = Constant.REDIRECT_URI;
            String SCOPE = Constant.SCOPE_USERINFO;

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);
            Log.info("第一步:用户授权, get Code URL:{"+getCodeUrl+"}");

            // 替换字符串，获得请求access token URL
            String tokenUrl = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            Log.info("第二步:get Access Token URL:{"+tokenUrl+"}");

            // 通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);

            JSONObject jsonObject = JSONObject.fromObject(response);
            Log.info("请求到的Access Token:{"+jsonObject.getString("access_token")+"}");

//            {
//                "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"SCOPE",
//                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//            }

            if (null != jsonObject) {
                try {

                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    Log.info("获取access_token成功!");
                    Log.info("WebAccessToken:{"+WebAccessToken+"} , openId:{"+openId+"}");

                    // 3. 使用获取到的 Access_token 和 openid 拉取用户信息
                    String userMessageUrl = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    Log.info("第三步:获取用户信息的URL:{}", userMessageUrl);

                    // 通过https方式请求获得用户信息响应
                    String userMessageResponse = HttpsUtil.httpsRequestToString(userMessageUrl, "GET", null);

                    JSONObject userMessageJsonObject = JSONObject.fromObject(userMessageResponse);

                    Log.info("用户信息:{}", userMessageJsonObject.toString());
//                    {
//                        "openid":" OPENID",
//                        "nickname": NICKNAME,
//                        "sex":"1",
//                        "province":"PROVINCE"
//                        "city":"CITY",
//                        "country":"COUNTRY",
//                        "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MoCfHe/46",
//                        "privilege":[
//                              "PRIVILEGE1"
//                              "PRIVILEGE2"
//                        ],
//                        "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//                    }

                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            nickName = userMessageJsonObject.getString("nickname");
                            //用户性别
                            sex = userMessageJsonObject.getString("sex");
                            sex = (sex.equals("1")) ? "男" : "女";
                            //用户唯一标识
                            openid = userMessageJsonObject.getString("openid");

                            Log.info("用户昵称:"+ nickName);
                            Log.info("用户性别:"+sex);
                            Log.info("OpenId:"+ openid);
                        } catch (JSONException e) {
                            Log.error("获取用户信息失败");
                        }
                    }
                } catch (JSONException e) {
                    Log.error("获取Web Access Token失败");
                }
            }
        }
        return "登录成功";
    }

}
