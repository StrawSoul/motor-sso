package com.motor.sso.server.config;

import com.motor.sso.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class ApplicationConfig {

    @Bean
    public SsoUserService userService(UserRepository userRepository, UserValidator userValidator, UserFactory userFactory, UserCache cache){
        return new SsoUserService(userRepository,userValidator, userFactory, cache);
    }

}
