package com;

import com.adcc.utility.log.Log;
import com.model.accesstoken.AccessToken;
import com.util.WeixinUtil;

/**
 * 菜单创建主方法
 */
public class MenuManagerApp {

    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wx83dad0407e617c02";
        // 第三方用户唯一凭证密钥
        String appSecret = "e2c222228707f8146b180c8335992bb1";

        // 调用接口获取access_token
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

        int result=0;
        if (at != null) {
            // 调用接口创建菜单
            result = WeixinUtil.createMenu(WeixinUtil.initMenu(), at.getAccess_token());
            // 判断菜单创建结果
            if (0 == result) {
                Log.info("菜单创建成功！");
            } else {
                Log.info("菜单创建失败，错误码：" + result);
            }
        }
    }
}
