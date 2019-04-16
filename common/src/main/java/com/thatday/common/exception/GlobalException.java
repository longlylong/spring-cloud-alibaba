package com.thatday.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -3872801766233608999L;
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg msg) {
        super(msg.getMsg());
        this.codeMsg = msg;
    }

    public GlobalException(String msg) {
        super(msg);
        this.codeMsg = CodeMsg.create(msg);
    }

    public GlobalException(Integer code, String msg) {
        super(msg);
        this.codeMsg = CodeMsg.create(msg);
        this.codeMsg.setCode(code);
    }
}
