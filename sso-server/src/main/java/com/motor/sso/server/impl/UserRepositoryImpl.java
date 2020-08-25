package com.motor.sso.server.impl;

import com.motor.common.domain.UnSupportRepository;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserRepository;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.server.impl.mapper.SsoUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
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
 * version: 0.0.0  2020/8/20 12:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class UserRepositoryImpl extends UnSupportRepository<String, SsoUser> implements UserRepository {

    @Autowired
    SsoUserMapper ssoUserMapper;

    public SsoUser findBySecurityKey(String key) {
        SsoUser ssoUser = ssoUserMapper.findBySecurityKey(key);
        UserSecurity userSecurity = ssoUserMapper.findSecurityByKey(key);
        HashMap<String, List<UserSecurity>> map = new HashMap<>();
        map.put("username", Arrays.asList(userSecurity));
        ssoUser.setSecurity(map);
        return ssoUser;
    }

    @Override
    public SsoUser findById(String id) {
        return ssoUserMapper.findById(id);
    }

    @Override
    public void insert(SsoUser entity) {

        ssoUserMapper.insert(entity);

        entity.getSecurity().values().forEach(e->{
            e.forEach(f->{
                ssoUserMapper.insertSecurity(f);
            });
        });
    }
}
