package com.motor.sso.core;

import com.motor.common.message.command.Command;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserLogin;
import com.motor.sso.core.command.UserSecurityValidate;

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
    UserValidator isRepeat(UserSecurityValidate command) ;


    UserValidator isUsernameLegal(String username) ;
    UserValidator isLegal(UserSecurityValidate command) ;

    UserValidator isRequired(String value, String desc) ;

    UserValidator validateSecurityKey(String securityKey, String securityValue) ;

    UserValidator validateUserSecurity(SsoUser user, UserLogin userLogin) ;

    UserValidator validateVerifyCode(UserSecurityValidate userSecurityValidate, String value);
}
