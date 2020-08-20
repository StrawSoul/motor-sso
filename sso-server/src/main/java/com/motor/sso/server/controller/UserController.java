package com.motor.sso.server.controller;

import com.motor.common.message.command.Command;
import com.motor.common.message.command.CommandBuilder;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.sso.core.UserService;
import com.motor.sso.core.command.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public ResultData test(){
        System.out.println(userService);
        return ResultBuilder.getInstance()
                .success()
                .build();
    }



    public ResultData<String> login(@RequestBody UserLogin userLogin){
        Command cmd = CommandBuilder.getInstance().build(userLogin);
        String token = userService.login(cmd);
        return ResultBuilder.getInstance(String.class)
                .data(token)
                .build();
    }
}
