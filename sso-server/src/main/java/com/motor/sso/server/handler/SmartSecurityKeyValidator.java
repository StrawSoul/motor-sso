package com.motor.sso.server.handler;

import com.motor.sso.core.command.UserSecurityValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
 * version: 0.0.0  2020/8/26 16:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class SmartSecurityKeyValidator {

    @Autowired
    private Map<String,SecurityKeyValidator> map ;


    public void validate(UserSecurityValidate securityValidate){
        SecurityKeyValidator securityKeyValidator = this.get(securityValidate.getType());
        if(securityKeyValidator != null){
            securityKeyValidator.validate(securityValidate.getKey());
        }
    }
    private SecurityKeyValidator get(String type){
        SecurityKeyValidator securityKeyValidator = map.get(type + "-validator");
        return securityKeyValidator;
    }

}
