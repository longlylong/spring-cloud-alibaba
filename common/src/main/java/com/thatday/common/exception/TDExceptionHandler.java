package com.thatday.common.exception;

import com.thatday.common.constant.StatusCode;
import com.thatday.common.controller.BaseController;
import com.thatday.common.model.Result;
import com.thatday.common.token.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
public class TDExceptionHandler {

    public static GlobalException throwGlobalException(String path, Exception e) {
        log.error("\nGlobalExceptionHandler | {}\n{}\n", path, e);
        e.printStackTrace();
        throw GlobalException.createError(e.getMessage());
    }

    public static GlobalException throwPermissionException() {
        return new GlobalException(StatusCode.Permission_Error, "权限不足!");
    }

    public static GlobalException throwTokenException() {
        return GlobalException.create(StatusCode.Token_Error, TokenConstant.Msg_Access_Token_Error);
    }

    /**
     * 异常处理逻辑
     */
    public static Result<Object> handle(String path, Exception e) {
        log.error("\nGlobalExceptionHandler | {}\n{}\n", path, e);
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.buildError(ex.getCodeMsg());

        } else if (e instanceof MethodArgumentNotValidException) {
            var exception = (MethodArgumentNotValidException) e;
            return BaseController.buildParamErrorResponseModel(exception.getBindingResult());

        } else if (e instanceof BindException) {
            var exception = ((BindException) e);
            return BaseController.buildParamErrorResponseModel(exception.getBindingResult());

        } else {
            return Result.buildError("操作失败，请联系客服");
        }
    }
}
