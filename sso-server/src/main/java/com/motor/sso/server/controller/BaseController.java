package com.motor.sso.server.controller;

import com.motor.common.message.command.Command;
import com.motor.common.message.command.CommandBuilder;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.sso.core.command.VerifyCodeSend;
import com.motor.sso.server.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
 * version: 0.0.0  2020/8/25 09:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Controller
@RequestMapping("base")
public class BaseController {

    @Autowired
    private BaseService baseService;

    @GetMapping(value = "/captcha")
    public void captcha(HttpServletRequest req, HttpServletResponse resp){

    }

    @PostMapping(value = "verification-code/send")
    @ResponseBody
    public ResultData sendVerifyCode(@RequestBody VerifyCodeSend verifyCodeSend){
        Command cmd = CommandBuilder.getInstance().data(verifyCodeSend).build();
        baseService.sendVerifyCode(cmd);
        return ResultBuilder.getInstance().success().build();
    }

}
