package com.motor.sso.core.exception;

import com.motor.common.exception.ErrorCode;

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
 * version: 0.0.0  2020/8/20 12:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public interface SSOUserErrorCode {
    ErrorCode USER_NOT_LOGIN = new ErrorCode("sso00000","USER_NOT_LOGIN","用户未登录");
    ErrorCode USER_OR_PASSWORD_WRONG = new ErrorCode("sso00001","USER_OR_PASSWORD_WRONG","用户名密码错误");
    ErrorCode USERNAME_IS_EMPTY = new ErrorCode("sso00002","USERNAME_IS_EMPTY","用户名不能为空");
    ErrorCode USERNAME_IS_WRONG = new ErrorCode("sso00003","USERNAME_IS_WRONG","用户名格式错误");
    ErrorCode VERIFY_CODE_IS_WRONG = new ErrorCode("sso00003","VERIFY_CODE_IS_WRONG","验证码错误");
}