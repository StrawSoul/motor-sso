package com.motor.sso.server.config;


import com.motor.common.message.result.ResultData;

/**
 * @author: zlj
 * @date: 2019-05-15 上午9:52
 * @description:
 */
public interface BusinessExceptionHandler<T extends Throwable> {

    ResultData handle(T e);
}
