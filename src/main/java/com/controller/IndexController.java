package com.controller;

import com.adcc.utility.log.Log;
import com.common.ResponseCode;
import com.dto.UserDTO;
import com.model.dbuser.DBUser;
import com.model.response.BaseResponse;
import com.model.response.LoginResponse;
import com.service.dbUser.DBUserService;
import com.util.Constant;
import com.util.HttpsUtil;
import com.util.UserInfoUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZP on 2017/10/27.
 */
@Controller
public class IndexController {

    @Autowired
    private DBUserService dbUserService;

    @RequestMapping("/vote.do")
    public ModelAndView listVote(@RequestParam(name="code",required=false)String code,
                                 @RequestParam(name="state")String state) {

        String WebAccessToken = "";
        String openId  = "";
        String nickName = "";
        String sex = "";

        System.out.println("-----------------------------收到请求，请求数据为："+code+"-----------------------"+state);

        //通过code换取网页授权web_access_token
        if(code != null || !(code.equals(""))){

            String APPID = Constant.appId;
            String SECRET = Constant.appSecret;
            String CODE = code;

            String REDIRECT_URI = "http://weixinzp.ngrok.xiaomiqiu.cn/vote.do";
//            String REDIRECT_URI = "http://18777q5t37.51mypc.cn/vote.do";
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
                            openId = userMessageJsonObject.getString("openid");

                            System.out.println("用户昵称------------------------"+nickName);
                            System.out.println("用户性别------------------------"+sex);
                            System.out.println("用户的唯一标识-------------------"+openId);
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

        Map result = new HashMap();
        result.put("openId", openId);

        DBUser dbUser = dbUserService.findByOpenId(openId);
        if (dbUser != null) {
            return new ModelAndView("loginPage");
        } else {
            return new ModelAndView("register", result);
        }
    }

    @RequestMapping("/userInfo.do")
    public ModelAndView getUserInfo(@RequestParam(name="code",required=false)String code,
                                 @RequestParam(name="state")String state) {
        String WebAccessToken = "";
        String openId  = "";
        String nickName = "";
        String sex = "";
        System.out.println("-----------------------------收到请求，请求数据为："+code+"-----------------------"+state);

        //通过code换取网页授权web_access_token
        if(code != null || !(code.equals(""))){
            String APPID = Constant.appId;
            String SECRET = Constant.appSecret;
            String CODE = code;

            String REDIRECT_URI = "http://weixinzp.ngrok.xiaomiqiu.cn/userInfo.do";
//            String REDIRECT_URI = "http://18777q5t37.51mypc.cn/userInfo.do";
            String SCOPE = "snsapi_userinfo";

            String getCodeUrl = UserInfoUtil.getCode(APPID, REDIRECT_URI, SCOPE);

            //替换字符串，获得请求URL
            String token = UserInfoUtil.getWebAccess(APPID, SECRET, CODE);
            //通过https方式请求获得web_access_token
            String response = HttpsUtil.httpsRequestToString(token, "GET", null);
            JSONObject jsonObject = JSONObject.fromObject(response);

            if (null != jsonObject) {
                try {
                    WebAccessToken = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");

                    //-----------------------拉取用户信息...替换字符串，获得请求URL
                    String userMessage = UserInfoUtil.getUserMessage(WebAccessToken, openId);
                    //通过https方式请求获得用户信息响应
                    String userMessageResponse = HttpsUtil.httpsRequestToString(userMessage, "GET", null);

                    JSONObject userMessageJsonObject = JSONObject.fromObject(userMessageResponse);

                    if (userMessageJsonObject != null) {
                        try {
                            //用户昵称
                            nickName = userMessageJsonObject.getString("nickname");
                            //用户性别
                            sex = userMessageJsonObject.getString("sex");
                            sex = (sex.equals("1")) ? "男":"女";
                            //用户唯一标识
                            openId = userMessageJsonObject.getString("openid");
                        } catch (JSONException e) {
                            Log.error("获取userName失败", e);
                        }
                    }
                } catch (JSONException e) {
                    Log.error("获取WebAccessToken失败", e);
                }
            }
        }

        Map result = new HashMap();
        result.put("openId", openId);
        result.put("nickName", nickName);
        result.put("sex", sex);
        result.put("WebAccessToken", WebAccessToken);
        if (openId.equals(Constant.WEXIN_OPENID_ZP)) {
            return new ModelAndView("AdminYM", result);
        } else if (openId.equals(Constant.WEXIN_OPENID_WM)) {
            return new ModelAndView("YouKeYM", result);
        } else {
            return new ModelAndView("index");
        }
    }

    @RequestMapping("/register.do")
    public ModelAndView goRegister() {
        return new ModelAndView("loginPage");
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginResponse login(@RequestBody UserDTO userDTO){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResponseCode(ResponseCode.SUCCESS);

        try {
            String name = userDTO.getName();
            String password = userDTO.getPassword();
            if(name == null || "".equals(name)
                    || password == null || "".equals(password)){
                loginResponse.setResponseCode(ResponseCode.PARAMETER_ERROR);
                loginResponse.setResponseDesc("用户名或者密码不能为空");
                return loginResponse;
            }
            DBUser dbUser = dbUserService.getUser(userDTO);
            if(dbUser == null){
                loginResponse.setResponseCode(ResponseCode.PARAMETER_ERROR);
                loginResponse.setResponseDesc("用户名或者密码不正确");
                return loginResponse;
            }
            loginResponse.setResponseDesc("登陆成功");
        } catch (Exception e) {
            Log.error("login error", e);
            loginResponse.setResponseCode(ResponseCode.SERVER_ERROR);
            loginResponse.setResponseDesc("服务器错误");
        }
        return loginResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public BaseResponse addUser(@RequestBody UserDTO userDTO){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResponseCode(ResponseCode.SUCCESS);
        try {
            String name = userDTO.getName();
            String password = userDTO.getPassword();
            String openId = userDTO.getOpenId();

            if(name == null || "".equals(name)){
                baseResponse.setResponseCode(ResponseCode.PARAMETER_ERROR);
                baseResponse.setResponseDesc("用户名不能为空");
                return baseResponse;
            }
            if(password == null || "".equals(password)){
                baseResponse.setResponseCode(ResponseCode.PARAMETER_ERROR);
                baseResponse.setResponseDesc("密码不能为空");
                return baseResponse;
            }

            DBUser dbUser2 = dbUserService.findByOpenId(openId);
            if (dbUser2 == null) {
                DBUser dbUser = new DBUser();
                dbUser.setOpenId(openId);
                dbUser.setName(name);
                dbUser.setPassword(password);
                dbUserService.createDBUser(dbUser);
                baseResponse.setResponseDesc("新增用户成功");
            }
        } catch (Exception e) {
            Log.error("addUser method error", e);
            baseResponse.setResponseCode(ResponseCode.SERVER_ERROR);
            baseResponse.setResponseDesc("服务器错误");
        }
        return baseResponse;
    }

}
