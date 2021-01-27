package com.thatday.user.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.thatday.common.exception.TDExceptionHandler;
import com.thatday.common.model.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
//这里会与sentinel的异常降级有冲突
//这里抓了异常sentinel就统计不到了
//可降级服务可以try来继续流程
@ControllerAdvice
@ResponseBody
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof BlockException) {
            return Result.buildSentinelError();

        } else if (e instanceof RpcException) {
            log.error(e);
            return Result.buildRPCError();
        }
        return TDExceptionHandler.handle(request.getRequestURI(), e);
    }
}
