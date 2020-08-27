package com.motor.sso.server.impl;

import com.google.common.base.Strings;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.common.utils.MotorUtils;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserRepository;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.core.UserValidator;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserLogin;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.server.handler.SmartSecurityKeyValidator;
import com.motor.sso.server.utils.SSOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.motor.sso.core.exception.SSOUserErrorCode.*;

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
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SmartSecurityKeyValidator smartSecurityKeyValidator;


    public UserValidator createAble(Command<UserCreate> command) {
        return this;
    }

    @Override
    public UserValidator isSecurityKeyRepeat(UserSecurityValidate userSecurityValidate) {
        String type = userRepository.findSecurityTypeByKey(userSecurityValidate.getKey());
        if(type != null){
            throw new BusinessRuntimeException(SECURITY_KEY_EXISTS,type );
        }
        return this;
    }

    public UserValidator isUsernameLegal(String username) {
        if (Strings.isNullOrEmpty(username)) {
            throw new BusinessRuntimeException(USERNAME_IS_EMPTY);
        }
        if(username.length() < 6){
            throw new BusinessRuntimeException(USERNAME_IS_WRONG);
        }
        return this;
    }

    public UserValidator isSecurityKeyLegal(UserSecurityValidate userSecurityValidate) {
        if(MotorUtils.isNull(userSecurityValidate)
                || MotorUtils.isNull(userSecurityValidate.getType())
                || MotorUtils.isNull(userSecurityValidate.getKey())){
            throw new BusinessRuntimeException(PARAMETER_LOST);
        }
        smartSecurityKeyValidator.validate(userSecurityValidate);
        return this;
    }


    @Override
    public UserValidator validateUserSecurity(SsoUser user, UserLogin userLogin) {
        Map<String, UserSecurity> security = user.getSecurity();
        if(security == null || security.get(userLogin.getType()) == null){
            throw new BusinessRuntimeException(USER_OR_PASSWORD_WRONG);
        }
        if(!Objects.deepEquals(user.getPassword(), SSOUtils.md5(user.getSalt(),userLogin.getSecurityValue()))){
            throw new BusinessRuntimeException(USER_OR_PASSWORD_WRONG);
        }
        return this;
    }

    @Override
    public UserValidator validateVerifyCode(String business,UserSecurityValidate userSecurityValidate) {
        captchaService.validateVerifyCode(business,userSecurityValidate);
        return this;
    }

    @Override
    public void isEmpty(UserSecurityValidate userSecurityValidate) {
        if(MotorUtils.isNull(userSecurityValidate)
                || MotorUtils.isNull(userSecurityValidate.getType())
                || MotorUtils.isNull(userSecurityValidate.getKey())
                || MotorUtils.isNull(userSecurityValidate.getValue())){
            throw new BusinessRuntimeException(PARAMETER_LOST);
        }
    }

    @Override
    public void lostParameter(Object obj) {
        if(obj == null){
            throw new BusinessRuntimeException(PARAMETER_LOST);
        }
    }


}
