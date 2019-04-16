package com.thatday.common.model;


import com.alibaba.fastjson.JSONObject;
import com.thatday.common.constant.StatusCode;
import com.thatday.common.exception.CodeMsg;
import com.thatday.common.exception.GlobalException;
import lombok.Data;
import lombok.var;

import java.io.Serializable;

@Data
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = 2882146120308441911L;

    // 响应业务状态
    protected Integer code;

    // 响应消息
    protected String message;

    // 响应数据
    protected Object data;

    private ResponseModel(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseModel buildParamError(String paramName) {
        return build(StatusCode.Parameter_Error, paramName);
    }

    public static String buildSentinelError() {
        var r = new JSONObject();
        r.put("code", StatusCode.Sentinel_Error);
        r.put("message", "操作过快");
        return r.toJSONString();
    }

    /**
     * 处理exception 错误
     */
    public static ResponseModel buildError(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return buildError(globalException.getCodeMsg());
        }
        return buildParamError("操作失败！");
    }

    public static ResponseModel buildError(CodeMsg err) {
        return build(err.getCode(), err.getMsg());
    }

    public static ResponseModel buildError(String err) {
        return buildParamError(err);
    }

    public static ResponseModel buildSuccess() {
        return buildSuccess(null);
    }

    public static ResponseModel buildSuccess(Object data) {
        return build(StatusCode.Success, "成功", data);
    }

    private static ResponseModel build(Integer code, String message, Object data) {
        return new ResponseModel(code, message, data);
    }

    public static ResponseModel build(Integer code, String message) {
        return build(code, message, null);
    }

    public boolean ok() {
        return code.equals(StatusCode.Success);
    }


}
