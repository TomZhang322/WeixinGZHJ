package com.model.dbuser;

/**
 * 微信登录注册用户实体
 */
public class DBUser {

    // id
    private long id;

    // 名字
    private String name;

    // 用户的标识
    private String openId;

    // 用户密码
    private String password;

    // 角色(0:普通人员 1：管理人员)
    private String roleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "DBUser{" +
                "id=" + id +
                ", name=" + name +
                ", openId='" + openId + '\'' +
                ", password='" + password + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
