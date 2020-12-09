package com.thatday.common.constant;

public interface UserCode {

    int UserNotExistCode = 10001;
    String UserNotExistMsg = "用户或密码错误";

    int UserNotValidatedCode = 10002;
    String UserNotValidatedMsg = "用户禁止登陆";

    int UserNotAuthorCode = 10003;
    String UserNotAuthorMsg = "用户没权限操作";
}
