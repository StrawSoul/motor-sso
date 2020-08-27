package com.motor.sso.server.impl;

import com.motor.sso.core.PrimaryKeyProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * version: 0.0.0  2020/8/25 16:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
@Service
public class SimplePrimaryKeyProducer implements PrimaryKeyProducer {
    private Map<String,Long> map = new ConcurrentHashMap<>();
    @Override
    public String produce(String businessCode) {
        String random = RandomStringUtils.random(3, "1234567890");
        return new StringBuilder().append(System.currentTimeMillis()).append(random).toString();
    }

    @Override
    public String[] produce(String businessCode, int n) {
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = produce(businessCode);
        }
        return arr;
    }
}
