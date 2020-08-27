package com.motor.sso.server.interceptor;

import com.motor.common.message.command.Command;
import com.motor.message.http.servlet.HttpServletCommandBuilder;
import com.motor.sso.client.CurrentUserRepository;
import com.motor.sso.core.SsoUserService;
import com.motor.sso.core.dto.SimpleUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * version: 0.0.0  2020/8/27 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class CurrentUserInterceptor implements HandlerInterceptor {

    @Autowired
    private CurrentUserRepository<SimpleUserInfo> currentUserRepository;

    @Autowired
    SsoUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Command cmd = HttpServletCommandBuilder.get().build();
        String token = cmd.token();
        SimpleUserInfo user = null;
        if(StringUtils.isEmpty(token)){
            token = userService.loginAsGuest(cmd);
            cmd = HttpServletCommandBuilder.get().token(token).build();
            user = userService.loadSimpleUserInfo(cmd);
        } else {
            user = userService.loadSimpleUserInfo(cmd);
            if(user == null){
                token = userService.loginAsGuest(cmd);
                cmd = HttpServletCommandBuilder.get().token(token).build();
                user = userService.loadSimpleUserInfo(cmd);
            }
        }
        HttpServletCommandBuilder.get().userId(user.getUserId());
        currentUserRepository.save(user);
        cmd.token(token);
        request.setAttribute("m-token",token);
        response.setHeader("m-token", token);
        response.addCookie(new Cookie("m-token", token));
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        currentUserRepository.remove();
    }
}
