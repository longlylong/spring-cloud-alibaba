package com.thatday.common.model;


import com.thatday.common.constant.StatusCode;
import com.thatday.common.exception.CodeMsg;
import com.thatday.common.exception.GlobalException;
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

    public static <T> Result<T> buildParamError(String paramName) {
        return build(StatusCode.Parameter_Error, paramName);
    }

    public static <T> Result<T> buildSentinelError() {
        return build(StatusCode.Sentinel_Error, "操作太快");
    }

    /**
     * 处理exception 错误
     */
    public static <T> Result<T> buildError(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return buildError(globalException.getCodeMsg());
        }
        return buildParamError("操作失败！");
    }

    public static <T> Result<T> buildError(CodeMsg err) {
        return build(err.getCode(), err.getMsg());
    }

    public static <T> Result<T> buildError(String err) {
        return buildParamError(err);
    }

    public static <T> Result<T> buildSuccess() {
        return buildSuccess(null);
    }

    public static <T> Result<T> buildSuccess(T data) {
        return build(StatusCode.SUCCESS, "成功", data);
    }

    private static <T> Result<T> build(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> build(Integer code, String message) {
        return build(code, message, null);
    }

    public boolean ok() {
        return code.equals(StatusCode.SUCCESS);
    }


}
