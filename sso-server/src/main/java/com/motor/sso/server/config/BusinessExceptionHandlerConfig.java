package com.motor.sso.server.config;

import com.motor.common.exception.BusinessException;
import com.motor.common.exception.BusinessRuntimeException;
import com.motor.common.message.result.ResultBuilder;
import com.motor.common.message.result.ResultData;
import com.motor.sso.server.constants.ValidErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.motor.sso.server.constants.ValidErrorCode.*;

/**
 * @author: zlj
 * @date: 2019-05-15 上午9:52
 * @description:
 */
@ControllerAdvice
public class BusinessExceptionHandlerConfig{
    private static Logger log = LoggerFactory.getLogger(BusinessExceptionHandlerConfig.class);

    private Map<Class, BusinessExceptionHandler> handlers;

    @PostConstruct
    public void init() {
        handlers = new ConcurrentHashMap<>();
        /**
         *  自定义异常处理
         */
        handlers.put(BusinessException.class, (BusinessExceptionHandler<BusinessException>) e -> ResultBuilder.getInstance()
                .failed(e)
                .build());
        handlers.put(BusinessRuntimeException.class, (BusinessExceptionHandler<BusinessRuntimeException>) e -> ResultBuilder.getInstance()
                .failed(e)
                .build());
        /**
         *  权限校验异常处理
         */
//        handlers.put(AuthorizationException.class, (BusinessExceptionHandler<AuthorizationException>)e -> new ResultData(AuthErrorCode.NO_PERMISSION.getCode(), "登录用户无权访问模块或操作功能", AuthErrorCode.NO_PERMISSION.getDesc(), null));
        /**
         *  对象参数接收请求体校验不通过
         */
        handlers.put(MethodArgumentNotValidException.class,
                (BusinessExceptionHandler<MethodArgumentNotValidException>) e -> ResultBuilder.getInstance()
                                .exception(e)
                                .code(METHOD_ARGUMENT_NOT_VALID_EXCEPTION)
                                .innerMessage("对象参数接收请求体校验不通过")
                                .userMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getDesc())
                                .build());
        /**
         *  普通参数校验校验不通过
         */
        handlers.put(ConstraintViolationException.class,
                (BusinessExceptionHandler<ConstraintViolationException>) e -> ResultBuilder.getInstance()
                        .exception(e)
                        .code(METHOD_ARGUMENT_NOT_VALID_EXCEPTION)
                        .innerMessage("普通参数校验校验不通过")
                        .userMessage(CONSTRAINT_VIOLATION.getDesc())
                        .build());
        /**
         *  必填参数没传校验不通过
         */
        handlers.put(ServletRequestBindingException.class,
                (BusinessExceptionHandler<ServletRequestBindingException>) e -> ResultBuilder.getInstance()
                        .exception(e)
                        .code(SERVLET_REQUEST_BINDING_EXCEPTION)
                        .innerMessage("必填参数没传校验不通过")
                        .userMessage(SERVLET_REQUEST_BINDING_EXCEPTION.getDesc())
                        .build());
        /**
         *  请求参数绑定到对象上校验不通过会
         */
        handlers.put(BindException.class,
                (BusinessExceptionHandler<BindException>) e -> ResultBuilder.getInstance()
                        .exception(e)
                        .code(BIND_EXCEPTION)
                        .innerMessage("请求参数绑定到对象上校验不通过会")
                        .userMessage(BIND_EXCEPTION.getDesc())
                        .build());
    }

    /**
     * 默认处理Exception异常，返回json结果
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultData<String> defaultErrorHandler(HttpServletRequest req, Exception e) {
        Throwable err = e.getCause() == null ? e : e.getCause();
        log.error("{} {} {}", err.getClass().getName(), err.getMessage(), err.getStackTrace()[0]);
        log.error("================全局异常：", e);
        if (handlers.containsKey(err.getClass())) {
            return handlers.get(err.getClass()).handle(err);
        } else {
            return ResultBuilder.getInstance().failed(e).userMessage("系统开小差了......").build();
        }
    }
}
