package com.thatday.common.exception;

import lombok.Data;

@Data
public class CodeMsg {

    public static final CodeMsg GOODS_NOT_FOUND = new CodeMsg(22001, "商品没找到！");

    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CodeMsg create(String msg) {
        return new CodeMsg(100, msg);
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    public CodeMsg contact(String errMsg) {
        this.msg = String.format("%s%s", this.msg, errMsg);
        return this;
    }
}
