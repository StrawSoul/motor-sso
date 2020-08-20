package com.motor.sso.core.command;

/**
 * ===========================================================================================
 * 设计说明
 * -------------------------------------------------------------------------------------------
 * <p>
 * ===========================================================================================
 * 方法简介
 * -------------------------------------------------------------------------------------------
 * {methodName}     ->  {description}
 * ===========================================================================================
 * 变更记录
 * -------------------------------------------------------------------------------------------
 * version: 0.0.0  2020/8/20 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class UserRegister {

    private UserSecurityValidate userSecurityValidate;
    private String username;

    public UserSecurityValidate getUserSecurityValidate() {
        return userSecurityValidate;
    }

    public void setUserSecurityValidate(UserSecurityValidate userSecurityValidate) {
        this.userSecurityValidate = userSecurityValidate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
