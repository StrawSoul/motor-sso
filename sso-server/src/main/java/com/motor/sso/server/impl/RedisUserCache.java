package com.motor.sso.server.impl;

import com.motor.sso.core.UserCache;
import com.motor.sso.core.dto.SimpleUserInfo;
import com.motor.sso.server.utils.SSOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

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
 * version: 0.0.0  2020/8/27 14:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class RedisUserCache implements UserCache {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    private final static String USER_KEY_TEMPLATE = "sso:user:%s";
    private final static String CODE_KEY_TEMPLATE = "sso:code:%s:%s";

    private final static String getUserKey(String token){
        return String.format(USER_KEY_TEMPLATE, token);
    }
    private final static String getCodeKey(String business,String  key){
        return String.format(CODE_KEY_TEMPLATE, business, key);
    }
    @Override
    public String save(SimpleUserInfo userInfo) {
        String key = SSOUtils.randomString(6);
        String token = SSOUtils.md5(key, userInfo.getUserId());
        redisTemplate.opsForValue().set(getUserKey(token), userInfo);
        redisTemplate.expire(token, 30, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public SimpleUserInfo get(String token) {
        SimpleUserInfo user = (SimpleUserInfo)redisTemplate.opsForValue().get(getUserKey(token));
        return user;
    }

    @Override
    public void remove(String token) {
        redisTemplate.delete(getUserKey(token));
    }

    @Override
    public String getVerifyCode(String business, String key) {
        String code = (String)redisTemplate.opsForValue().get(getCodeKey(business,key));
        return code;
    }

    @Override
    public void setVerifyCode(String business, String key, String value) {
        redisTemplate.opsForValue().set(getCodeKey(business,key), value);
        redisTemplate.expire(getCodeKey(business,key), 3, TimeUnit.MINUTES);
    }

}
