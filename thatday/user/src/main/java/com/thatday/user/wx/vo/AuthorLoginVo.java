package com.thatday.user.wx.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorLoginVo {

    @NotNull(message = "code不能为空!")
    @ApiModelProperty(value = "code", required = true)
    private String code;

    @ApiModelProperty(value = "encryptedData", required = true)
    @NotNull(message = "encryptedData不能为空!")
    private String encryptedData;

    @ApiModelProperty(value = "iv;", required = true)
    @NotNull(message = "iv不能为空!")
    private String iv;

    @ApiModelProperty(value = "手机的sdk版本")
    private String deviceSdkVersion;

    @ApiModelProperty(value = "手机型号")
    private String deviceModel;

    @ApiModelProperty(value = "手机系统")
    private String deviceSystem;

}
