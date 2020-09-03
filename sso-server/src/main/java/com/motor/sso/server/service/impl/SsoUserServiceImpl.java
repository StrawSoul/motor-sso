package com.motor.sso.server.service.impl;

import com.motor.common.message.command.Command;
import com.motor.common.validator.BaseValidator;
import com.motor.sso.client.CurrentUserRepository;
import com.motor.sso.core.*;
import com.motor.sso.core.command.*;
import com.motor.sso.core.dto.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;

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
public class SsoUserServiceImpl implements SsoUserService {

    private UserRepository userRepository;

    private UserValidator userValidator;

    private UserFactory userFactory;

    private UserCache cache;

    private BaseValidator baseValidator = BaseValidator.getInstance();

    private CurrentUserRepository<SimpleUserInfo> currentUserRepository;

    public SsoUserServiceImpl(UserRepository userRepository, UserValidator userValidator, UserFactory userFactory, UserCache cache, CurrentUserRepository<SimpleUserInfo> currentUserRepository) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userFactory = userFactory;
        this.cache = cache;
        this.currentUserRepository = currentUserRepository;
    }

    public String register(Command<UserRegister> command){

        UserRegister data = command.data();

        Map<String, UserSecurityValidate> security = data.getSecurity();

        userValidator.lostParameter(security);

        UserSecurityValidate userSecurityValidate = security.get(data.getVerifyType());

        userValidator.lostParameter(userSecurityValidate);


        for (Map.Entry<String, UserSecurityValidate> entry : security.entrySet()) {
            UserSecurityValidate securityValidate = entry.getValue();
            securityValidate.setType(entry.getKey());
            userValidator.isSecurityKeyLegalAndNotRepeat(securityValidate);
        }

        userValidator.validateVerifyCode(BUSINESS_REGISTER,userSecurityValidate);

        SsoUser user = userFactory.createUserForRegister(command, currentUserRepository.get());

        userRepository.insert(user);

        return user.getId();
    }

    public void validateUserSecurity(Command<UserSecurityValidate> command){

        UserSecurityValidate data = command.data();

        userValidator.isSecurityKeyLegalAndNotRepeat(data);

    }

    public String loginAsGuest(Command command){
        SimpleUserInfo simpleUserInfo = userFactory.createGuest();
        String token = cache.save(simpleUserInfo);
        return token;
    }

    public String login(Command<UserLogin> command){

        UserLogin data = command.data();

        userValidator.isEmpty(new UserSecurityValidate(data.getType(),data.getSecurityKey(), data.getSecurityValue()));

        SsoUser user = userRepository.findBySecurityKey(data.getSecurityKey());

        userValidator.validateUserSecurity(user, data);

        SimpleUserInfo simpleUserInfo = userFactory.createSimpleInfo(user);

        String token = cache.save(simpleUserInfo);

        cache.remove(command.token());

        return token;
    }

    public SimpleUserInfo loadSimpleUserInfo(Command cmd){

        String token = cmd.token();

        baseValidator.isEmpty("token", token);

        SimpleUserInfo simpleUserInfo = cache.get(token);

        return simpleUserInfo;
    }


    public void logout(Command<UserLogout> cmd) {

        UserLogout data = cmd.getData();

        cache.remove(data.getToken());

    }

    public void modifyPassword(Command cmd) {

    }

    public void usernameExits(Command<String> cmd) {
        userValidator.isSecurityKeyRepeat(new UserSecurityValidate("username", cmd.getData()));
    }
}