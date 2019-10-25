package com.thatday.common.constant;

public interface StatusCode {

    //成功
    int SUCCESS = 0;

    //参数错误
    int Parameter_Error = 100;

    //token校验错误
    int Token_Error = 200;

    //操作过快
    int Sentinel_Error = 429;

}
