package com.motor.sso.server.impl;

import com.motor.common.message.command.Command;
import com.motor.sso.core.PrimaryKeyProducer;
import com.motor.sso.core.SsoUser;
import com.motor.sso.core.UserFactory;
import com.motor.sso.core.UserSecurity;
import com.motor.sso.core.command.UserCreate;
import com.motor.sso.core.command.UserEdit;
import com.motor.sso.core.command.UserRegister;
import com.motor.sso.core.command.UserSecurityValidate;
import com.motor.sso.core.dto.SimpleUserInfo;
import com.motor.sso.server.utils.SSOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class UserFactoryImpl implements UserFactory {

    @Autowired
    private PrimaryKeyProducer primaryKeyProducer;

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
        UserSecurityValidate userSecurityValidate = userRegister.getUserSecurity();

        String userId = primaryKeyProducer.produce("sso-user");

        SsoUser ssoUser = new SsoUser();
        ssoUser.setId(userId);
        ssoUser.setDeleted(false);
        ssoUser.setStatus(0);
        ssoUser.setUsername(userRegister.getUsername());

        String[] ids = primaryKeyProducer.produce("sso-user-security", 2);
        UserSecurity userSecurity = new UserSecurity();
        String salt = RandomStringUtils.random(5);
        String pwd = SSOUtils.md5(salt, userRegister.getPassword());
        userSecurity.setId(ids[0]);
        userSecurity.setSalt(salt);
        userSecurity.setSecurityValue(pwd);
        userSecurity.setSecurityKey(userRegister.getUsername());
        userSecurity.setDeleted(false);
        userSecurity.setUserId(userId);

        UserSecurity userSecurity2 = new UserSecurity();
        userSecurity2.setId(ids[1]);
        userSecurity2.setType(userSecurityValidate.getType());
        userSecurity2.setSecurityKey(userSecurityValidate.getKey());
        userSecurity2.setDeleted(false);
        userSecurity2.setUserId(userId);

        Map<String, List<UserSecurity>>  map = new HashMap<>();

        map.put("username", Arrays.asList(userSecurity));
        map.put(userSecurityValidate.getType(), Arrays.asList(userSecurity2));

        ssoUser.setSecurity(map);

        return ssoUser;
    }

    public SimpleUserInfo createSimpleInfo(SsoUser user) {
        SimpleUserInfo simpleUserInfo = new SimpleUserInfo();
        simpleUserInfo.setUsername(user.getUsername());
        return simpleUserInfo;
    }
}
