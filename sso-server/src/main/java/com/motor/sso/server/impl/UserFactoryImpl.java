package com.motor.sso.server.impl;

import com.motor.common.message.command.Command;
import com.motor.sso.client.CurrentUserRepository;
import com.motor.sso.core.PrimaryKeyProducer;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserFactory;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserEdit;
import com.motor.sso.core.command.UserRegister;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.core.dto.SimpleUserInfo;
import com.motor.sso.server.constants.UserSecurityType;
import com.motor.sso.server.utils.SSOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
public class UserFactoryImpl implements UserFactory {

    @Autowired
    private PrimaryKeyProducer primaryKeyProducer;

    @Autowired
    private CurrentUserRepository<SimpleUserInfo> currentUserRepository;

    public SsoUser create(Command<UserCreate> command) {
        UserCreate userCreate = command.getData();
        SsoUser ssoUser = new SsoUser();
        ssoUser.setDeleted(false);
        ssoUser.setStatus(0);
//        ssoUser.setUsername(userCreate);
        return null;
    }

    public SsoUser edit(SsoUser user, Command<UserEdit> command) {
        return null;
    }

    public SsoUser createUserForRegister(Command<UserRegister> command) {
        UserRegister userRegister = command.getData();
        Map<String, UserSecurityValidate> security = userRegister.getSecurity();

        String userId = null;
        SimpleUserInfo simpleUserInfo = currentUserRepository.get();
        if(simpleUserInfo != null){
            userId = simpleUserInfo.getUserId();
        } else {
            userId = primaryKeyProducer.produce("sso-user");
        }
        String salt = SSOUtils.randomString(5);
        String pwd = SSOUtils.md5(salt, userRegister.getPassword());

        SsoUser ssoUser = new SsoUser();
        ssoUser.setId(userId);
        ssoUser.setPassword(pwd);
        ssoUser.setSalt(salt);
        ssoUser.setDeleted(false);
        ssoUser.setStatus(0);


        String[] ids = primaryKeyProducer.produce("sso-user-security", security.size());
        Map<String, UserSecurity>  map = new HashMap<>();
        AtomicInteger i = new AtomicInteger(0);
        security.forEach((k,v)->{

            UserSecurity userSecurity = new UserSecurity();

            userSecurity.setId(ids[i.getAndAdd(1)]);
            userSecurity.setType(v.getType());
            userSecurity.setSecurityKey(v.getKey());
            userSecurity.setDeleted(false);
            userSecurity.setUserId(ssoUser.getId());
            map.put(v.getType(), userSecurity);
        });
        ssoUser.setSecurity(map);

        return ssoUser;
    }

    public SimpleUserInfo createSimpleInfo(SsoUser user) {
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        UserSecurity userSecurity = user.getSecurity().get(UserSecurityType.username.name());
        if(userSecurity != null){
            simpleUserInfo.setUsername(userSecurity.getSecurityKey());
        }
        simpleUserInfo.setNickname(user.getNickname());
        simpleUserInfo.setUserId(user.getId());
        return simpleUserInfo;
    }

    public SimpleUserInfo createGuest(){
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        String userId = primaryKeyProducer.produce("sso-user");
        simpleUserInfo.setUserId(userId);
        simpleUserInfo.setGuest(true);
        return simpleUserInfo;
    }
}
