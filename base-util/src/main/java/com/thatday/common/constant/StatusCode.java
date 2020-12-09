package com.thatday.common.constant;

public interface StatusCode {

    //成功
    int SUCCESS = 0;

    //参数错误
    int Parameter_Error = 90100;

    //权限错误
    int Permission_Error = 90300;

    //token校验错误
    int Token_Error = 90400;

    //限流
    int Sentinel_Error = 90429;

    //异常错误
    int Exception_Error = 90500;

    //服务期间调用错误
    int RPC_Error = 90600;

}
