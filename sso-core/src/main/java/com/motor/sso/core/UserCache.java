package com.motor.sso.core;

import com.motor.sso.core.dto.SimpleUserInfo;

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
 * version: 0.0.0  2020/8/20 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public interface UserCache {

    String BUSINESS_REGISTER = "register";
    String BUSINESS_LOGIN = "login";
    String BUSINESS_MODIFY_PASSWORD = "modifyPassword";
    public String save(SimpleUserInfo userInfo);

    SimpleUserInfo get(String token);

    String getVerifyCode(String business, String key);
    void setVerifyCode(String business, String key, String value);

    void remove(String token);
}
