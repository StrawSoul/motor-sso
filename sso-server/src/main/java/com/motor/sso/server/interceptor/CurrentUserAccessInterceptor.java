package com.motor.sso.server.interceptor;

import com.motor.common.exception.BusinessRuntimeException;
import com.motor.sso.client.CurrentUserRepository;
import com.motor.sso.core.dto.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * version: 0.0.0  2020/8/27 13:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class CurrentUserAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private CurrentUserRepository<SimpleUserInfo> currentUserRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SimpleUserInfo simpleUserInfo = currentUserRepository.get();
        if(simpleUserInfo == null || simpleUserInfo.isGuest()){
            throw new BusinessRuntimeException(USER_NOT_LOGIN);
        }
        return true;
    }
}
