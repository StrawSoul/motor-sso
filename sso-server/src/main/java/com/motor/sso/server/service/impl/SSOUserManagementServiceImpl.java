package com.motor.sso.server.service.impl;

import com.motor.common.exception.BaseErrorCode;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.common.validator.BaseValidator;
import com.motor.sso.core.*;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.server.constants.UserSecurityType;
import org.apache.commons.lang3.StringUtils;

import static com.motor.sso.core.exception.SSOUserErrorCode.PARAMETER_LOST;

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
public class SSOUserManagementServiceImpl implements SSOUserManagementService {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private UserFactory userFactory;

    public SSOUserManagementServiceImpl(UserRepository userRepository, UserValidator userValidator, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userFactory = userFactory;
    }

    public String createUser(Command<UserCreate> command){
        command.userId();
        userValidator.createAble(command.data());
        SsoUser user = userFactory.create(command);
        userRepository.insert(user);
        return user.getId();
    }
}
