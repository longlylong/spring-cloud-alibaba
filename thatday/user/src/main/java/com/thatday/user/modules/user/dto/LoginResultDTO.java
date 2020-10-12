package com.thatday.user.modules.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginResultDTO {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "用信息")
    private Object userInfo;
}
