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
public class SsoUserService {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserFactory userFactory;
    private UserCache cache;

    public SsoUserService(UserRepository userRepository, UserValidator userValidator, UserFactory userFactory, UserCache cache) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userFactory = userFactory;
        this.cache = cache;
    }

    public String register(Command<UserRegister> command){

        UserRegister data = command.data();
        UserSecurityValidate userSecurityValidate = data.getUserSecurity();
        String value = cache.getVerifyCode("register",userSecurityValidate.getKey());

        userValidator.isUsernameLegal(data.getUsername())
        .isLegal(userSecurityValidate)
        .isRepeat(userSecurityValidate)
        .validateVerifyCode(userSecurityValidate, value);

        SsoUser user = userFactory.createUserForRegister(command);

        userRepository.insert(user);

        return user.getId();
    }
    public void edit(Command<UserEdit> command){
        UserEdit data = command.data();
        SsoUser oldUser = userRepository.findById(data.getId());
        SsoUser newUser = userFactory.edit(oldUser, command);
        userRepository.update(newUser);
    }

    public void validateUserSecurity(Command<UserSecurityValidate> command){
        UserSecurityValidate data = command.data();
        userValidator.isLegal(data);
        userValidator.isRepeat(data);
    }

    public String login(Command<UserLogin> command){
        UserLogin data = command.data();
        userValidator.isRequired(data.getSecurityValue(), "密码不能为空");
        SsoUser user = userRepository.findBySecurityKey(data.getSecurityKey());
        userValidator.validateUserSecurity(user, data);
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


    public void logout(Command<UserLogout> cmd) {

    }

    public void modifyPassword(Command cmd) {

    }

    public void usernameExits(Command cmd) {

    }
}
