package com.motor.sso.client;

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
 * version: 0.0.0  2020/8/27 11:00  zlj
 * 创建
 * -------------------------------------------------------------------------------------------
 * version: 0.0.1  {date}       {author}
 * <p>
 * ===========================================================================================
 */
public class CurrentUserRepository<T> {
    private ThreadLocal<T> threadLocal = new ThreadLocal<>();

    public void save(T t){
        threadLocal.set(t);
    }
    public T get(){
        return threadLocal.get();
    }
    public void remove(){
        threadLocal.remove();
    }

}