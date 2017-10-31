package com.controller;

import com.util.Constant;
import com.util.HttpsUtil;
import com.util.UserInfoUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ZP on 2017/10/27.
 */
@Controller
public class IndexController {

    String openid = "";

    @RequestMapping("/vote.do")
    public ModelAndView listVote(@RequestParam(name="code",required=false)String code,
                                 @RequestParam(name="state")String state) {

        System.out.println("-----------------------------收到请求，请求数据为："+code+"-----------------------"+state);

        //通过code换取网页授权web_access_token
        if(code != null || !(code.equals(""))){

            String APPID = Constant.appId;
            String SECRET = Constant.appSecret;
            String CODE = code;
            String WebAccessToken = "";
            String openId  = "";
            String nickName,sex = "";
            String REDIRECT_URI = "http://weixinzp.ngrok.xiaomiqiu.cn/vote.do";
            String SCOPE = "snsapi_userinfo";

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);
            System.out.println("---------------getCodeUrl--------------"+getCodeUrl);

            //替换字符串，获得请求URL
            String token = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            System.out.println("----------------------------token为："+token);
            //通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(token, "GET", null);
            JSONObject jsonObject = JSONObject.fromObject(response);
            System.out.println("jsonObject------"+jsonObject);

            if (null != jsonObject) {
                try {
                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    System.out.println("获取access_token成功-------------------------"+WebAccessToken+"----------------"+openId);

                    //-----------------------拉取用户信息...替换字符串，获得请求URL
                    String userMessage = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    System.out.println(" userMessage==="+ userMessage);
                    //通过https方式请求获得用户信息响应
                    String userMessageResponse = HttpsUtil.httpsRequestToString(userMessage, "GET", null);

                    JSONObject userMessageJsonObject = JSONObject.fromObject(userMessageResponse);

                    System.out.println("userMessagejsonObject------"+userMessageJsonObject);

                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            nickName = userMessageJsonObject.getString("nickname");
                            //用户性别
                            sex = userMessageJsonObject.getString("sex");
                            sex = (sex.equals("1")) ? "男":"女";
                            //用户唯一标识
                            openid = userMessageJsonObject.getString("openid");

                            System.out.println("用户昵称------------------------"+nickName);
                            System.out.println("用户性别------------------------"+sex);
                            System.out.println("用户的唯一标识-------------------"+openid);
                        } catch (JSONException e) {
                            System.out.println("获取userName失败");
                        }
                    }
                } catch (JSONException e) {
                    WebAccessToken = null;// 获取code失败
                    System.out.println("获取WebAccessToken失败");
                }
            }
        }

        //此处业务代码省略 ^_^
//        return new ModelAndView("mypages/index", model);
        if (openid.equals(Constant.WEXIN_OPENID_ZP)) {
            return new ModelAndView("AdminYM");
        } else if (openid.equals(Constant.WEXIN_OPENID_WM)) {
            return new ModelAndView("YouKeYM");
        } else {
            return new ModelAndView("index");
        }
//        return new ModelAndView("index");

    }
}
