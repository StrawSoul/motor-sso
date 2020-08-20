package com.motor.sso.server.impl;

import com.motor.common.message.command.Command;
import com.motor.sso.core.User;
import com.motor.sso.core.UserFactory;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserEdit;
import com.motor.sso.core.command.UserRegister;
import com.motor.sso.core.dto.SimpleUserInfo;
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
public class UserFactoryImpl implements UserFactory {

    public User create(Command<UserCreate> command) {
        return null;
    }

    public User edit(User user, Command<UserEdit> command) {
        return null;
    }

    public User createUserForRegister(Command<UserRegister> command) {
        return null;
    }

    public SimpleUserInfo createSimpleInfo(User user) {
        return null;
    }
}
