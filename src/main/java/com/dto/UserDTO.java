package com.dto;

import com.model.dbuser.DBUser;

import java.util.List;

/**
 * Created by ZP on 2017/11/6.
 */
public class UserDTO extends DBUser {

    private String accessToken;

    private List<String> roleTypeList;

    //确认密码
    private String confirmPassword;

    //新密码
    private String newPassword;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getRoleTypeList() {
        return roleTypeList;
    }

    public void setRoleTypeList(List<String> roleTypeList) {
        this.roleTypeList = roleTypeList;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
