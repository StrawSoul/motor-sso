package com.motor.sso.server.handler;

import com.motor.common.exception.ParameterLegalException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.motor.sso.server.constants.ValidErrorCode.PARAM_LEGAL_ERROR;

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
@Service("mobile-validator")
public class MobileValidator implements SecurityKeyValidator {

    private static  final Pattern compile = Pattern.compile("^(\\+86)?[0-9]{11}$");

    @Override
    public void validate(String key) {

        if(!compile.matcher(key).matches()){
            throw new ParameterLegalException(PARAM_LEGAL_ERROR,"手机号","不符合规则");
        }

    }
}
