package com.motor.sso.server.controller;

import com.motor.common.message.command.Command;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.message.http.servlet.HttpServletCommandBuilder;
import com.motor.sso.core.SSOUserManagementService;
import com.motor.sso.core.command.UserCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
 * version: 0.0.0  2020/8/31 09:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@RestController
@RequestMapping("management/user")
public class SSOUserManagementController {

    @Autowired
    SSOUserManagementService ssoUserManagementService;

    @PostMapping("create")
    public ResultData<String>  create(@RequestBody UserCreate userCreate){
        Command cmd = HttpServletCommandBuilder.get().data(userCreate).build();
        String id = ssoUserManagementService.createUser(cmd);
        return ResultBuilder.getInstance()
                .data(id)
                .success()
                .build();
    }
}
