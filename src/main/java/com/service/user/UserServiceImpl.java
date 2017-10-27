package com.service.user;

import com.model.user.WeixinUserInfo;
import com.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ZP on 2017/10/26.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("userService")
public class UserServiceImpl implements UserService {

    // 获取用户关注列表 限500（次/天）
    public static String userList_get_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";

    // 获取指定用户基本信息 限500 000（次/天）
    public static String userInfo_get_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";

    @Override
    public JSONObject getUserList(String accessToken) {
        JSONObject jsonObject = null;
        try {
            // 拼装获取用户列表的url
            String url = userList_get_url.replace("ACCESS_TOKEN", accessToken);
            // 调用接口查询用户列表
            jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo userInfo = new WeixinUserInfo();
        try {
            // 拼装获取用户基本信息的url
            String url = userInfo_get_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
            // 调用接口查询用户基本信息
            JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
            Map map = jsonObject;
            if (map.get("subscribe") != null) {
                userInfo.setSubscribe(Integer.parseInt(map.get("subscribe").toString()));
            }
            if (map.get("openid") != null) {
                userInfo.setOpenId(map.get("openid").toString());
            }
            if (map.get("nickname") != null) {
                userInfo.setNickname(map.get("nickname").toString());
            }
            if (map.get("sex") != null) {
                userInfo.setSex(Integer.parseInt(map.get("sex").toString()));
            }
            if (map.get("language") != null) {
                userInfo.setLanguage(map.get("language").toString());
            }
            if (map.get("city") != null) {
                userInfo.setCity(map.get("city").toString());
            }
            if (map.get("province") != null) {
                userInfo.setProvince(map.get("province").toString());
            }
            if (map.get("country") != null) {
                userInfo.setCountry(map.get("country").toString());
            }
            if (map.get("headimgurl") != null) {
                userInfo.setHeadImgUrl(map.get("headimgurl").toString());
            }
            if (map.get("subscribe_time") != null) {
                userInfo.setSubscribeTime(map.get("subscribe_time").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
