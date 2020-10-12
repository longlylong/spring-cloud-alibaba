package com.thatday.user.wx.open.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WXOpenLoginDTO {

    //    {
//        "access_token":"34_SF_V-7ogXGz1l0eJE0bSVDI5BFMtrAqpedUTV4yXS3LC0JSCIbmHic9VrQyjNL6ZiDyZAy_XTxg6ksEOpu2hYrEt0RT_rTM2ChEf8HT1H1Y",
//            "expires_in":7200,
//            "refresh_token":"34_PLXg68VaSpm7K8w9Ywqm4HIrFv0GjyqF9pEF3rjdRzlzJbkicB4aNwduk4kyYGvt20kyWNCk_0qPHnASNh9VkzB3AWQ5fRaTB31FFCjkEck",
//            "openid":"oOBKs1fxVD0Pld4_qTP4Tc8Kc8Mk",
//            "scope":"snsapi_login",
//            "unionid":"oVM9d1uYY-dPzBR3NoNVH0Bg3I1w"
//    }
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
}
