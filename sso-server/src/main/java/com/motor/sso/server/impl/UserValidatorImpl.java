package com.motor.sso.server.impl;

import com.google.common.base.Strings;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.core.UserValidator;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserLogin;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.server.utils.SSOUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
@Service
public class UserValidatorImpl implements UserValidator {
    public UserValidator createAble(Command<UserCreate> command) {
        return this;
    }

    public UserValidator isRepeat(UserSecurityValidate command) {

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

    public UserValidator isLegal(UserSecurityValidate command) {
        return this;
    }

    public UserValidator isRequired(String value, String desc) {
        if (Strings.isNullOrEmpty(value)) {
            throw new RuntimeException(desc);
        }
        return this;
    }

    public UserValidator validateSecurityKey(String securityKey, String securityValue) {

        return this;
    }

    @Override
    public UserValidator validateUserSecurity(SsoUser user, UserLogin userLogin) {

        Map<String, List<UserSecurity>> security = user.getSecurity();
        if(security == null || security.get(userLogin.getType()) == null){
            throw new BusinessRuntimeException(USER_OR_PASSWORD_WRONG);
        }

        UserSecurity userSecurity = security.get(userLogin.getType()).stream()
                .filter(e -> Objects.equals(e.getSecurityKey(), userLogin.getSecurityKey()))
                .findFirst()
                .orElseThrow(()->new BusinessRuntimeException(USER_OR_PASSWORD_WRONG));

        if(!Objects.deepEquals(userSecurity.getSecurityValue(), SSOUtils.md5(userSecurity.getSalt(),userLogin.getSecurityValue()))){
            throw new BusinessRuntimeException(USER_OR_PASSWORD_WRONG);
        }
        return this;
    }

    @Override
    public UserValidator validateVerifyCode(UserSecurityValidate userSecurityValidate, String value) {
        if (!Objects.deepEquals(userSecurityValidate.getValue(), value)) {
            throw new BusinessRuntimeException(VERIFY_CODE_IS_WRONG);
        }
        return this;
    }


}
