package com.controller;

import com.adcc.utility.log.Log;
import com.service.menu.MenuService;
import com.thread.AccessTokenThread;
import com.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 对订阅号的菜单的操作
 *
 */
@RestController
@RequestMapping("/menu")

public class MenuController {
    @Autowired
    private MenuService menuService;

    //查询全部菜单
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String getMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getAccess_token();
        JSONObject jsonObject =null;
        if (at != null) {
            // 调用接口查询菜单
            jsonObject = menuService.getMenu(at);
            // 判断菜单创建结果
            return String.valueOf(jsonObject);
        }
        Log.info("token为"+at);
        return "无数据";
    }

    //创建菜单
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public int createMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getAccess_token();
        int result=0;
        if (at != null) {
            // 调用接口创建菜单
            result = menuService.createMenu(WeixinUtil.initMenu(), at);
            // 判断菜单创建结果
            if (0 == result) {
                Log.info("菜单创建成功！");
            } else {
                Log.info("菜单创建失败，错误码：" + result);
            }
        }
        return result ;
    }

    //删除菜单
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public int deleteMenu() {
        // 调用接口获取access_token
        String at = AccessTokenThread.accessToken.getAccess_token();
        int result=0;
        if (at != null) {
            // 删除菜单
            result = menuService.deleteMenu(at);
            // 判断菜单删除结果
            if (0 == result) {
                Log.info("菜单删除成功！");
            } else {
                Log.info("菜单删除失败，错误码：" + result);
            }
        }
        return  result;
    }

}
