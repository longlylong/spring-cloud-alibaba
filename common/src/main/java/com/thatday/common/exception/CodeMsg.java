package com.thatday.common.exception;

import com.thatday.common.constant.StatusCode;
import lombok.Data;

@Data
public class CodeMsg {

    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CodeMsg create(int code, String msg) {
        return new CodeMsg(code, msg);
    }

    public static CodeMsg createParam(String msg) {
        return create(StatusCode.Parameter_Error, msg);
    }

    public static CodeMsg createError(String msg) {
        return create(StatusCode.Exception_Error, msg);
    }

}
