package com.thatday.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -3872801766233608999L;
    private CodeMsg codeMsg;

    public static GlobalException createParam(String msg) {
        return new GlobalException(CodeMsg.createParam(msg));
    }

    public static GlobalException createError(String msg) {
        return new GlobalException(CodeMsg.createError(msg));
    }

    public static GlobalException create(CodeMsg codeMsg) {
        return new GlobalException(codeMsg);
    }

    public static GlobalException create(int code, String msg) {
        return new GlobalException(code, msg);
    }

    public GlobalException(CodeMsg msg) {
        super(msg.getMsg());
        this.codeMsg = msg;
    }

    public GlobalException(int code, String msg) {
        super(msg);
        this.codeMsg = CodeMsg.create(code, msg);
    }
}
