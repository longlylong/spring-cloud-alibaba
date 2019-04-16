package com.thatday.common.constant;

public interface StatusCode {
    /**
     * 0     表示成功
     * 10000 用户模块的状态码
     * 20000 商品模块的状态码
     * 30000 订单模块的状态码
     * 40000 支付模块的状态码
     */

    //成功
    int Success = 0;

    //参数错误
    int Parameter_Error = 100;

    //操作过快
    int Sentinel_Error = 10;

    //token校验错误
    int Token_Error = 200;

    String Des_User_Info = "用户信息无效或过期,请重新登录";

    String Des_User_Info_Null = "UserInfo不能为空！";
}
