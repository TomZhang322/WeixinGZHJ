package com.service.user;

import com.model.user.WeixinUserInfo;
import net.sf.json.JSONObject;

/**
 * 用户操作接口
 */
public interface UserService {

    /**
     * 获取用户关注列表
     * @param accessToken
     * @return
     */
    public JSONObject getUserList(String accessToken);

    /**
     * 获取指定用户基本信息
     * @param accessToken
     * @param openId
     * @return
     */
    public WeixinUserInfo getUserInfo(String accessToken, String openId);


}
