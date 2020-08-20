package com.motor.sso.server.impl;

import com.motor.common.message.command.Command;
import com.motor.sso.core.User;
import com.motor.sso.core.UserValidator;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserSecurityValidate;
import org.springframework.stereotype.Service;

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
 * version: 0.0.0  2020/8/20 13:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class UserValidatorImpl implements UserValidator {
    public void createAble(Command<UserCreate> command) {

    }

    public void isRepeat(UserSecurityValidate command) {

    }

    public void isUsernameLegal(String username) {

    }

    public void isLegal(UserSecurityValidate command) {

    }

    public void isRequired(String value, String desc) {

    }

    public void validateSecurityKey(String securityKey, String securityValue) {

    }

    public void validateUserSecurity(User user, String securityKey, String securityValue) {

    }
}
