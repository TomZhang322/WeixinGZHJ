package com.service.dbUser;

import com.adcc.utility.log.Log;
import com.mapper.DBUserMapper;
import com.model.dbuser.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信公众号注册用户接口服务实现类
 */
@Service("DBUserService")
public class DBUserServiceImpl implements DBUserService {

    @Autowired
    private DBUserMapper dbUserMapper;

    @Override
    public DBUser findByOpenId(String openId) {
        DBUser dbUser = null;
        try {
            dbUser = dbUserMapper.findByOpenId(openId);
        } catch (Exception e) {
            Log.error("DBUser findByOpenId method error", e);
        }
        return dbUser;
    }

    @Override
    public DBUser getUser(DBUser dbUser) {
        DBUser dbUserResult = null;
        try {
            dbUserResult = dbUserMapper.getUser(dbUser);
        } catch (Exception e) {
            Log.error("DBUser getUser method error", e);
        }
        return dbUserResult;
    }

    @Override
    public void createDBUser(DBUser dbUser) {
        try {
            dbUserMapper.createDBUser(dbUser);
        } catch (Exception e) {
            Log.error("createDBUser method error", e);
        }
    }

    @Override
    public void updateDBUser(DBUser dbUser) {
        try {
            dbUserMapper.updateDBUser(dbUser);
        } catch (Exception e) {
            Log.error("updateDBUser method error", e);
        }
    }

    @Override
    public void deleteDBUser(String openId) {
        try {
            dbUserMapper.deleteDBUser(openId);
        } catch (Exception e) {
            Log.error("deleteDBUser method error", e);
        }
    }
}
