package com.motor.sso.core;

import com.motor.common.message.command.Command;
import com.motor.sso.core.command.UserCreate;

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
public class UserManagementService {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserFactory userFactory;

    public String createUser(Command<UserCreate> command){
        userValidator.createAble(command);
        SsoUser user = userFactory.create(command);
        userRepository.insert(user);
        return user.getId();
    }
}
