package com.motor.sso.core;

import com.motor.common.message.command.Command;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserEdit;
import com.motor.sso.core.command.UserRegister;
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
 * version: 0.0.0  2020/8/20 10:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public interface UserFactory {

    SsoUser create(Command<UserCreate> command);

    SsoUser createUserForRegister(Command<UserRegister> command, SimpleUserInfo currentUser);

    SimpleUserInfo createSimpleInfo(SsoUser user);

    SimpleUserInfo createGuest();
}
