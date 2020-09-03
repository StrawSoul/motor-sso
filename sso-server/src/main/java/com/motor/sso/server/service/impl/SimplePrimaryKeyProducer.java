package com.motor.sso.server.service.impl;

import com.motor.common.domain.DefaultPrimaryKeyProducer;
import com.motor.common.domain.PrimaryKeyProducer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

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
public class SimplePrimaryKeyProducer extends DefaultPrimaryKeyProducer implements PrimaryKeyProducer {
    private Map<String,String> map;

    public SimplePrimaryKeyProducer(Map<String, String> map) {
        this.map = new ConcurrentHashMap<>(map);
    }

    @Override
    public String produce(String businessCode) {
        return super.produce(prefix(businessCode));
    }

    @Override
    public String[] produce(String businessCode, int n) {
        return super.produce(prefix(businessCode), n);
    }

    private String prefix(String businessCode){
        return map.getOrDefault(businessCode, businessCode);
    }
}
