package com.thatday.common.token;

public interface TokenConstant {

    long Token_Expires = 2 * 24 * 60 * 60 * 1000L;
    String Secret = "hds##hsh55578*&1";

    String Msg_Access_Token_Error = "用户信息无效或过期";
    String Msg_Access_Token_Empty = "用户信息缺失";

    String TOKEN = "Authorization";

    String ACCESS_TOKEN = "accessToken";
    String USER_ID = "userId";
    String ROLE = "role";
    String DEVICE_ID = "deviceId";
    String EXPIRES_TIME = "expireTime";
    String CREATE_TIME = "createTime";

    String DEFAULT_AVATAR = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";
}
