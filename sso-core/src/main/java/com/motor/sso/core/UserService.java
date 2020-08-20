package com.motor.sso.core;

import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.sso.core.command.*;
import com.motor.sso.core.dto.SimpleUserInfo;

import static com.motor.sso.core.exception.SSOUserErrorCode.USER_NOT_LOGIN;

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
public class UserService {
    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserFactory userFactory;
    private UserCache cache;

    public UserService(UserRepository userRepository, UserValidator userValidator, UserFactory userFactory, UserCache cache) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userFactory = userFactory;
        this.cache = cache;
    }

    public String register(Command<UserRegister> command){
        UserRegister data = command.data();
        UserSecurityValidate userSecurityValidate = data.getUserSecurityValidate();
        userValidator.isRequired(data.getUsername(),"用户名不鞥能为空");
        userValidator.isUsernameLegal(data.getUsername());
        userValidator.isLegal(userSecurityValidate);
        userValidator.isRepeat(userSecurityValidate);
        User user = userFactory.createUserForRegister(command);
        userRepository.insert(user);
        return user.getId();
    }
    public void edit(Command<UserEdit> command){
        UserEdit data = command.data();
        User oldUser = userRepository.findById(data.getId());
        User newUser = userFactory.edit(oldUser, command);
        userRepository.update(newUser);
    }

    public void validateUserSecurity(Command<UserSecurityValidate> command){
        UserSecurityValidate data = command.data();
        userValidator.isLegal(data);
        userValidator.isRepeat(data);
    }
    public String login(Command<UserLogin> command){
        UserLogin data = command.data();
        userValidator.isRequired(data.getSecurityValue(), "密码不能唯恐");
        User user = userRepository.findBySecurityKey(data.getSecurityKey());
        userValidator.validateUserSecurity(user,data.getSecurityKey(), data.getSecurityValue());
        SimpleUserInfo simpleUserInfo = userFactory.createSimpleInfo(user);
        String token = cache.save(simpleUserInfo);
        return token;
    }

    public SimpleUserInfo loadSimpleUserInfo(String token){
        userValidator.isRequired(token,"token");
        SimpleUserInfo simpleUserInfo = cache.get(token);
        if(simpleUserInfo == null){
            throw new BusinessRuntimeException(USER_NOT_LOGIN);
        }
        return simpleUserInfo;
    }



}
