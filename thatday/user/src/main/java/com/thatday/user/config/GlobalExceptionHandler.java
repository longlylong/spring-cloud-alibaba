package com.thatday.user.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.thatday.common.exception.TDExceptionHandler;
import com.thatday.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BlockException) {
            return Result.buildSentinelError();
        }
        return TDExceptionHandler.handle(request.getRequestURI(), e);
    }
}
