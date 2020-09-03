package com.motor.sso.server.service.impl;

import com.motor.common.domain.UnSupportRepository;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserRepository;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.server.constants.UserSecurityType;
import com.motor.sso.server.mapper.SsoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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
public class MybatisUserRepository extends UnSupportRepository<String, SsoUser> implements UserRepository {

    @Autowired
    SsoUserMapper ssoUserMapper;

    public SsoUser findBySecurityKey(String key) {
        SsoUser ssoUser = ssoUserMapper.findBySecurityKey(key);
        UserSecurity userSecurity = ssoUserMapper.findSecurityByKey(key);
        HashMap<String, UserSecurity> map = new HashMap<>();
        map.put(UserSecurityType.username.name(), userSecurity);
        ssoUser.setSecurity(map);
        return ssoUser;
    }

    @Override
    public String findSecurityTypeByKey(String key){
        String type = ssoUserMapper.findSecurityTypeByKey(key);
        return type;
    }

    @Override
    public SsoUser findById(String id) {
        return ssoUserMapper.findById(id);
    }

    @Override
    @Transactional
    public void insert(SsoUser entity) {

        ssoUserMapper.insert(entity);

        entity.getSecurity().values().forEach(e->{
            ssoUserMapper.insertSecurity(e);
        });
    }
}
