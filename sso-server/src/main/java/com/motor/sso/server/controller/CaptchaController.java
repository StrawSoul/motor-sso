package com.motor.sso.server.controller;

import com.motor.common.message.command.Command;
import com.motor.common.message.command.CommandBuilder;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.message.http.servlet.HttpServletCommandBuilder;
import com.motor.sso.core.command.VerifyCodeSend;
import com.motor.sso.server.command.CaptchaCreate;
import com.motor.sso.server.impl.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService baseService;

    @GetMapping(value = "image/{business}")
    public void captcha(@PathVariable String business, HttpServletRequest req, HttpServletResponse response){
        Command cmd = HttpServletCommandBuilder.get().data(new CaptchaCreate(business)).build();
        BufferedImage captcha = baseService.createCaptcha(cmd);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("image/jpeg");
            response.addHeader("Pragma", "No-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0l);
            ImageIO.write(captcha, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "verification/{business}")
    @ResponseBody
    public ResultData sendVerifyCode(@PathVariable String business, @RequestBody VerifyCodeSend verifyCodeSend){
        verifyCodeSend.setBusiness(business);
        Command cmd = HttpServletCommandBuilder.get().data(verifyCodeSend).build();
        baseService.sendVerifyCode(cmd);
        return ResultBuilder.getInstance().success().build();
    }

}
