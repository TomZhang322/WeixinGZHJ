package com.mapper;

import com.model.dbuser.DBUser;

/**
 * Created by ZP on 2017/10/30.
 */
public interface DBUserMapper {

    public DBUser findByOpenId(String openId);

    public DBUser getUser(DBUser dbUser);

    public void createDBUser(DBUser dbUser);

    public void updateDBUser(DBUser dbUser);

    public void deleteDBUser(String openId);

}
