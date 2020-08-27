package com.motor.sso.server.mapper;

import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserSecurity;
import org.springframework.stereotype.Repository;

import java.util.List;

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
 * version: 0.0.0  2020/8/25 13:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Repository
public interface SsoUserMapper {

    SsoUser findById(String id);

    SsoUser findBySecurityKey(String key);

    UserSecurity findSecurityByKey(String key);

    void insert(SsoUser entity);

    void insertSecurity(UserSecurity f);

    String findSecurityTypeByKey(String key);
}
