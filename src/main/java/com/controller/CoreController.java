package com.controller;

import com.adcc.utility.log.Log;
import com.service.core.CoreService;
import com.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
public class CoreController {
    @Autowired
    private CoreService coreService;

    //验证是否来自微信服务器的消息
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
                                 @RequestParam(name = "nonce",required = false) String  nonce ,
                                 @RequestParam(name = "timestamp",required = false) String  timestamp ,
                                 @RequestParam(name = "echostr",required = false) String  echostr){
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            Log.info("接入成功");
            return echostr;
        }
        Log.error("接入失败");
        return "";
    }
    // 调用核心业务类接收消息、处理消息跟推送消息
    @RequestMapping(value = "",method = RequestMethod.POST)
    public  String post(HttpServletRequest req){
        String respMessage = coreService.processRequest(req);
        return respMessage;
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String weixinRedirect(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=你的appid&redirect_uri=你的服务器处理地址?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
    }
}