package com.thatday.config;

import com.thatday.common.exception.TDExceptionHandler;
import com.thatday.common.model.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionHandler(HttpServletRequest request, Exception e) {
        return TDExceptionHandler.handle(request.getRequestURI(), e);
    }
}
