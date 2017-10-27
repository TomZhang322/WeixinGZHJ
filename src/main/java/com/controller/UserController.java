package com.controller;

import com.adcc.utility.log.Log;
import com.service.user.UserService;
import com.thread.AccessTokenThread;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZP on 2017/10/26.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //查询关注者用户列表
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getUserList() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getAccess_token();
        JSONObject jsonObject =null;
        if (at != null) {
            // 调用接口查询用户列表
            jsonObject = userService.getUserList(at);
            // 判断用户列表创建结果
            return String.valueOf(jsonObject);
        }
        Log.info("token为"+at);
        return "无数据";
    }
}
