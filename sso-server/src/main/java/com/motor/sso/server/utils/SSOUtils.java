package com.motor.sso.server.utils;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;

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
 * version: 0.0.0  2020/8/25 15:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class SSOUtils {

    public static String md5(String key, String value){
        String md5 = Hashing.hmacMd5(key.getBytes()).hashBytes(value.getBytes()).toString();
        return md5;
    }
    public static String captcha(){
        String random = RandomStringUtils.random(6, "0123456789");
        return random;
    }
}
