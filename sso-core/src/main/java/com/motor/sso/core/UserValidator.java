package com.motor.sso.core;

import com.motor.common.message.command.Command;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserLogin;
import com.motor.sso.core.command.UserSecurityValidate;

import java.util.Map;

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
public interface UserValidator {

    UserValidator createAble(Command<UserCreate> command) ;

    default UserValidator isSecurityKeyLegalAndNotRepeat(UserSecurityValidate userSecurityValidate){
        isSecurityKeyLegal(userSecurityValidate);
        isSecurityKeyRepeat(userSecurityValidate);
        return this;
    }

    UserValidator isSecurityKeyRepeat(UserSecurityValidate userSecurityValidate) ;

    UserValidator isSecurityKeyLegal(UserSecurityValidate userSecurityValidate) ;

    UserValidator validateUserSecurity(SsoUser user, UserLogin userLogin) ;

    UserValidator validateVerifyCode(String business, UserSecurityValidate userSecurityValidate);

    void isEmpty(UserSecurityValidate userSecurityValidate);

    void lostParameter(Object obj);
}
