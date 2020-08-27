package com.motor.sso.server.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.motor.sso.core.UserCache;
import com.motor.sso.core.dto.SimpleUserInfo;
import com.motor.sso.server.utils.SSOUtils;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class LocalMapUserCache implements UserCache {

    private Map<String, SimpleUserInfo> cache = new ConcurrentHashMap<>();

    private Map<String,String> verifyCodes = new ConcurrentHashMap<>();




    public String save(SimpleUserInfo userInfo) {
        String key = SSOUtils.randomString(6);
        String token = SSOUtils.md5(key, userInfo.getUserId());
        cache.put(token, userInfo);
        return token;
    }

    public SimpleUserInfo get(String token) {
        return cache.get(token);
    }

    @Override
    public String getVerifyCode(String business, String key) {
        return verifyCodes.get(business+":"+ key);
    }

    @Override
    public void setVerifyCode(String business, String key, String value) {
        verifyCodes.put(business+":"+ key, value);
    }

    @Override
    public void remove(String token) {
        cache.remove(token);
    }

}
