package com.motor.sso.server.controller;

import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.message.http.servlet.HttpServletCommandBuilder;
import com.motor.sso.client.CurrentUserRepository;
import com.motor.sso.core.SsoUserService;
import com.motor.sso.core.command.*;
import com.motor.sso.core.dto.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
 * version: 0.0.0  2020/8/20 13:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@RestController
@RequestMapping("user")
public class SsoUserController {

    @Autowired
    private SsoUserService ssoUserService;

    @Autowired
    private CurrentUserRepository<SimpleUserInfo> currentUserRepository;

    @GetMapping("simple-info")
    public ResultData simpleInfo(){

        SimpleUserInfo simpleUserInfo = currentUserRepository.get();

        if(simpleUserInfo == null){
            throw new BusinessRuntimeException(USER_NOT_LOGIN);
        }

        return ResultBuilder.getInstance()
                .data(simpleUserInfo)
                .success()
                .build();
    }

    @PostMapping("security-key/validate")
    public ResultData usernameExits(@RequestBody UserSecurityValidate userSecurityValidate){
        Command cmd = HttpServletCommandBuilder.get().data(userSecurityValidate).build();
        ssoUserService.validateUserSecurity(cmd);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }

    @PostMapping("register")
    public ResultData register(@RequestBody UserRegister userRegister){
        Command cmd = HttpServletCommandBuilder.get().data(userRegister).build();
        ssoUserService.register(cmd);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }

    @PostMapping("login")
    public ResultData<String> login(@RequestBody UserLogin userLogin, HttpServletResponse response){
        Command cmd = HttpServletCommandBuilder.get().build(userLogin);
        String token = ssoUserService.login(cmd);
        response.setHeader("m-token", token);
        response.addCookie(new Cookie("m-token", token));
        return ResultBuilder.getInstance(String.class)
                .token(cmd.token())
                .data(token)
                .success()
                .build();
    }

    @PostMapping("logout")
    public ResultData logout(@RequestBody UserLogout userLogin){
        Command cmd = HttpServletCommandBuilder.get().build(userLogin);
        ssoUserService.logout(cmd);
        return ResultBuilder.getInstance().success()
                .build();
    }

    @PostMapping("password/modify")
    public ResultData modifyPassword(@RequestBody UserModifyPassword userModifyPassword){
        Command cmd = HttpServletCommandBuilder.get().build(userModifyPassword);
        ssoUserService.modifyPassword(cmd);
        return ResultBuilder.getInstance().success()
                .build();
    }
}
