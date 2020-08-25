package com.motor.sso.server.controller;

import com.motor.common.message.command.Command;
import com.motor.common.message.command.CommandBuilder;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.sso.core.SsoUserService;
import com.motor.sso.core.command.UserLogin;
import com.motor.sso.core.command.UserLogout;
import com.motor.sso.core.command.UserModifyPassword;
import com.motor.sso.core.command.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("test")
    public ResultData test(){
        System.out.println(ssoUserService);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }

    @GetMapping("username/exits")
    public ResultData usernameExits(String username){
        Command cmd = CommandBuilder.getInstance().data(username).build();
        ssoUserService.usernameExits(cmd);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }

    @PostMapping("register")
    public ResultData register(@RequestBody UserRegister userRegister){
        Command cmd = CommandBuilder.getInstance().data(userRegister).build();
        ssoUserService.register(cmd);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }

    @PostMapping("login")
    public ResultData<String> login(@RequestBody UserLogin userLogin){
        Command cmd = CommandBuilder.getInstance().build(userLogin);
        String token = ssoUserService.login(cmd);
        return ResultBuilder.getInstance(String.class)
                .data(token)
                .success()
                .build();
    }

    @PostMapping("logout")
    public ResultData logout(@RequestBody UserLogout userLogin){
        Command cmd = CommandBuilder.getInstance().build(userLogin);
        ssoUserService.logout(cmd);
        return ResultBuilder.getInstance().success()
                .build();
    }

    @PostMapping("password/modify")
    public ResultData modifyPassword(@RequestBody UserModifyPassword userModifyPassword){
        Command cmd = CommandBuilder.getInstance().build(userModifyPassword);
        ssoUserService.modifyPassword(cmd);
        return ResultBuilder.getInstance().success()
                .build();
    }


}
