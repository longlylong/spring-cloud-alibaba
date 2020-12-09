package com.thatday.common.model;


import com.thatday.common.constant.StatusCode;
import com.thatday.common.exception.CodeMsg;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.token.TokenConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2882146120308441911L;

    // 响应业务状态
    protected Integer code;

    // 响应消息
    protected String message;

    // 响应数据
    protected T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void set(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result<Object> buildTokenError() {
        return Result.build(StatusCode.Token_Error, TokenConstant.Msg_Access_Token_Error);
    }

    public static <T> Result<T> buildParamError(String paramName) {
        return build(StatusCode.Parameter_Error, paramName);
    }

    public static <T> Result<T> buildExceptionError(String errorMsg) {
        return build(StatusCode.Exception_Error, errorMsg);
    }

    public static <T> Result<T> buildRPCError() {
        return build(StatusCode.RPC_Error, "服务维护中");
    }

    public static <T> Result<T> buildSentinelError() {
        return build(StatusCode.Sentinel_Error, "操作太快");
    }

    public static <T> Result<T> buildExceptionError(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return buildExceptionError(globalException.getCodeMsg());
        }
        return buildParamError("操作失败！");
    }

    public static <T> Result<T> buildExceptionError(CodeMsg err) {
        return build(err.getCode(), err.getMsg());
    }

    public static <T> Result<T> buildSuccess() {
        return buildSuccess(null);
    }

    public static <T> Result<T> buildSuccess(T data) {
        return build(StatusCode.SUCCESS, "成功", data);
    }

    public static <T> Result<T> build(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> build(Integer code, String message) {
        return build(code, message, null);
    }

    public boolean ok() {
        return code.equals(StatusCode.SUCCESS);
    }


}
