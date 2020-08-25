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
@Service
public class UserCacheImpl implements UserCache {

    private Cache<String, SimpleUserInfo> cache;

    private Map<String,String> verifyCodes = new ConcurrentHashMap<>();

    public UserCacheImpl() {
    }

    public synchronized Cache<String, SimpleUserInfo> cache(){
        if(cache == null){
            cache = CacheBuilder.newBuilder().build();
        }
        return cache;
    }

    public String save(SimpleUserInfo userInfo) {
        String key = String.valueOf(System.currentTimeMillis());
        String token = SSOUtils.md5(key, userInfo.getUsername());
        cache().put(token, userInfo);
        return token;
    }

    public SimpleUserInfo get(String token) {
        return null;
    }

    @Override
    public String getVerifyCode(String business, String key) {
        return verifyCodes.get(business+":"+ key);
    }

    @Override
    public void setVerifyCode(String business, String key, String value) {

        verifyCodes.put(business+":"+ key, value);
    }

}
