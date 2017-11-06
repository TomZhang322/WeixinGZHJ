package com.service.dbUser;

import com.model.dbuser.DBUser;

/**
 * 微信公众号注册用户接口服务
 */
public interface DBUserService {

    /**
     * 根据openId查询
     * @param openId
     * @return
     */
    public DBUser findByOpenId(String openId);

    public DBUser getUser(DBUser dbUser);

    /**
     * 微信公众号账号注册
     * @param dbUser
     */
    public void createDBUser(DBUser dbUser);

    /**
     * 修改用户信息
     * @param dbUser
     */
    public void updateDBUser(DBUser dbUser);

    /**
     * 根据openId删除用户信息
     * @param openId
     */
    public void deleteDBUser(String openId);

}
