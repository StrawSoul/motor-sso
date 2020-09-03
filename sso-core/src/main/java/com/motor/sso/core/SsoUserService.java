package com.motor.sso.core;

import com.motor.common.message.command.Command;
import com.motor.common.validator.BaseValidator;
import com.motor.sso.core.command.*;
import com.motor.sso.core.dto.SimpleUserInfo;

import java.util.Map;

import static com.motor.sso.core.UserCache.BUSINESS_REGISTER;

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
public interface SsoUserService {

    String loginAsGuest(Command command);

    void usernameExits(Command<String> cmd);

    void validateUserSecurity(Command<UserSecurityValidate> command);

    String register(Command<UserRegister> command);

    String login(Command<UserLogin> command);

    void logout(Command<UserLogout> cmd);

    SimpleUserInfo loadSimpleUserInfo(Command<String> cmd);

    void modifyPassword(Command cmd);
}
