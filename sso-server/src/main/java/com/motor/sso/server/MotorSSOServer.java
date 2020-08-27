package com.motor.sso.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

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

@SpringBootApplication(scanBasePackages = "com.motor.sso.server")
@ImportResource(locations = { "classpath*:/spring/*.xml"})
@EnableAutoConfiguration
@MapperScan("com.motor.sso.server.mapper")
public class MotorSSOServer {
    static Logger logger = LoggerFactory.getLogger(MotorSSOServer.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MotorSSOServer.class, args);
        logger.info("启动成功");
    }
}
