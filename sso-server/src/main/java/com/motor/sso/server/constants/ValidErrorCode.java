package com.motor.sso.server.constants;


import com.motor.common.exception.ErrorCode;

public class ValidErrorCode {
    /**
     * 对象参数接收请求体校验不通过
     */
    public final static ErrorCode METHOD_ARGUMENT_NOT_VALID_EXCEPTION = new ErrorCode("VALID00001", "METHOD_ARGUMENT_NOT_VALID_EXCEPTION", "对象参数接收请求体校验不通过。");
    
    /**
     * 普通参数校验校验不通过
     */
    public final static ErrorCode CONSTRAINT_VIOLATION = new ErrorCode("VALID00002", "CONSTRAINT_VIOLATION", "普通参数校验校验不通过。");
    
    /**
     * 必填参数没传校验不通过
     */
    public final static ErrorCode SERVLET_REQUEST_BINDING_EXCEPTION = new ErrorCode("VALID00003", "SERVLET_REQUEST_BINDING_EXCEPTION", "必填参数没传校验不通过。");
    
    /**
     * 请求参数绑定到对象上校验不通过会
     */
    public final static ErrorCode BIND_EXCEPTION = new ErrorCode("VALID00004", "USER_LOCKING", "请求参数绑定到对象上校验不通过会。");

    public final static ErrorCode PARAM_LEGAL_ERROR = new ErrorCode("VALID00005", "PARAM_LEGAL_ERROR", "参数不符合规则");

}
