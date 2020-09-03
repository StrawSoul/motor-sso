package com.motor.sso.server.application;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
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

@SpringBootApplication(scanBasePackages = { "com.motor.sso.server"})
@ImportResource({ "classpath*:/spring/*.xml"})
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ibdp.persistent.gateways"})
@MapperScan("com.motor.sso.server.mapper")
public class MotorSSOCloudServer {
    static Logger logger = LoggerFactory.getLogger(MotorSSOCloudServer.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MotorSSOCloudServer.class, args);
        logger.info("启动成功");
    }
}
