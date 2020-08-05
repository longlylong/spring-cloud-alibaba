package com.thatday.common.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thatday.common.constant.StatusCode;
import com.thatday.common.model.Result;
import com.thatday.common.token.TokenConstant;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
        if (e instanceof GlobalException || e instanceof MethodArgumentNotValidException || e instanceof BindException) {
            log.error("\nGlobalExceptionHandler | {}\n", path + " | " + e.getMessage());
        } else {
            log.error("\nGlobalExceptionHandler | {}\n{}\n", path, e);
            e.printStackTrace();
        }

        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return Result.buildExceptionError(ex.getCodeMsg());

        } else if (e instanceof MethodArgumentNotValidException) {
            var exception = (MethodArgumentNotValidException) e;
            return buildParamErrorResponseModel(exception.getBindingResult());

        } else if (e instanceof BindException) {
            var exception = ((BindException) e);
            return buildParamErrorResponseModel(exception.getBindingResult());

        } else if (e instanceof InvalidFormatException) {
            return Result.buildExceptionError("不能输入小数或非法字符");

        } else if (e instanceof HttpMessageNotReadableException) {
            if (e.getMessage().contains("InvalidFormatException")) {
                return Result.buildParamError("不能输入小数或非法字符");
            } else {
                return Result.buildExceptionError("操作失败，请联系客服. " + e.getMessage());
            }
        } else {
            return Result.buildExceptionError("操作失败，请联系客服（" + e.getClass().getSimpleName() + "）");
        }
    }

    private static Result<Object> buildParamErrorResponseModel(BindingResult result) {
        var sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage());
            break;
        }

        String msg = sb.toString();
        if (TokenConstant.Msg_Access_Token_Error.equals(msg)) {
            return Result.build(StatusCode.Token_Error, msg);
        }

        return Result.buildParamError(sb.toString());
    }
}
