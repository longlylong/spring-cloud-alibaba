package com.thatday.common.constant;

public interface StatusCode {

    //成功
    int SUCCESS = 0;

    //参数错误
    int Parameter_Error = 10000;

    //权限错误
    int Permission_Error = 30000;

    //token校验错误
    int Token_Error = 40000;

    //异常错误
    int Exception_Error = 50000;

    //限流
    int Sentinel_Error = 42900;

}
