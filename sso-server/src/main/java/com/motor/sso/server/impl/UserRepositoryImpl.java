package com.motor.sso.server.impl;

import com.motor.common.domain.UnSupportRepository;
import com.motor.sso.core.User;
import com.motor.sso.core.UserRepository;
import org.springframework.stereotype.Service;

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
 * version: 0.0.0  2020/8/20 12:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class UserRepositoryImpl extends UnSupportRepository<String, User> implements UserRepository {
    public User findBySecurityKey(String key) {
        return null;
    }

}
