package com.thatday.user.wx.mp.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MPRedirectUrlVo {

    @NotEmpty(message = "跳转链接不能为空")
    @ApiModelProperty(value = "跳转链接", required = true)
    private String redirectURI;

    @NotEmpty(message = "授权范围不能为空")
    @ApiModelProperty(value = "授权范围 snsapi_base无感 snsapi_userinfo网页授权", required = true)
    private String scope;
}
