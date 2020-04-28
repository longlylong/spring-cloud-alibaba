package com.thatday.common.constant;

public interface StatusCode {

    //成功
    int SUCCESS = 0;

    //参数错误
    int Parameter_Error = 100;

    //异常错误
    int Exception_Error = 101;

    //权限错误
    int Permission_Error = 102;

    //token校验错误
    int Token_Error = 200;

    //操作过快
    int Sentinel_Error = 429;

}
