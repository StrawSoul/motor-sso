package com.motor.sso.server.impl;

import com.motor.common.message.command.Command;
import com.motor.sso.core.UserCache;
import com.motor.sso.core.command.VerifyCodeSend;
import com.motor.sso.server.utils.SSOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * version: 0.0.0  2020/8/25 16:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class BaseService {

    private final  static Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    UserCache userCache;

    public void sendVerifyCode(Command<VerifyCodeSend> cmd) {
        VerifyCodeSend data = cmd.getData();
        String captcha = SSOUtils.captcha();
        logger.debug("verify code is : {} {}", data.getKey(), captcha);
        userCache.setVerifyCode("register", data.getKey(), captcha);
    }
}
