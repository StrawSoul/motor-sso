package com.motor.sso.server.impl;

import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.command.Command;
import com.motor.common.utils.MotorUtils;
import com.motor.sso.core.UserCache;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.core.command.VerifyCodeSend;
import com.motor.sso.server.command.CaptchaCreate;
import com.motor.sso.server.utils.SSOUtils;
import com.motor.sso.server.utils.VerificationCodeImgUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static com.motor.sso.core.exception.SSOUserErrorCode.CAPTCHA_IS_WRONG;
import static com.motor.sso.core.exception.SSOUserErrorCode.VERIFY_CODE_IS_WRONG;

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
 * version: 0.0.0  2020/8/25 16:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class CaptchaService {

    private final  static Logger logger = LoggerFactory.getLogger(CaptchaService.class);

    @Autowired
    UserCache userCache;

    public void sendVerifyCode(Command<VerifyCodeSend> cmd) {
        VerifyCodeSend data = cmd.getData();
        String verifyCode = SSOUtils.verifyCode();
        String captcha = data.getCaptcha();
        String business = data.getBusiness();
        String captchaCache = userCache.getVerifyCode(business, cmd.token());
        if(!StringUtils.isEmpty(captchaCache)){
            logger.info("captcha {} {} {}=>{}",business, cmd.token(),captchaCache, captcha );
            if (!Objects.deepEquals(captchaCache, captcha)) {
                throw new BusinessRuntimeException(CAPTCHA_IS_WRONG);
            }
        }
        userCache.setVerifyCode(data.getBusiness(), data.getKey(), verifyCode);
        //todo send mobile or email verify message to user
        logger.debug("verify code is : {} {}", data.getKey(), verifyCode);

    }

    public BufferedImage createCaptcha(Command<CaptchaCreate> cmd) {
        CaptchaCreate data = cmd.getData();
        String captcha = SSOUtils.captcha();
        logger.debug("captcha is : {} {}", cmd.token(), captcha);
        VerificationCodeImgUtil verificationCodeImgUtil = VerificationCodeImgUtil.getInstance(72, 42);
        verificationCodeImgUtil.initVerificationCode(captcha);
        userCache.setVerifyCode(data.getBusiness() , cmd.token(), captcha);
        BufferedImage image = verificationCodeImgUtil.getImage();
        return image;
    }

    public void validateVerifyCode(String business, UserSecurityValidate userSecurityValidate) {
        String verifyCode = userCache.getVerifyCode(business, userSecurityValidate.getKey());
        if (!Objects.deepEquals(userSecurityValidate.getValue(), verifyCode)) {
            throw new BusinessRuntimeException(VERIFY_CODE_IS_WRONG);
        }
    }
}
