package com.thatday.user.modules.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginPhoneVo {


    @NotBlank(message = "手机号不能为空！")
    @Size(min = 11, max = 11, message = "手机长度必须是11位！")
    @ApiModelProperty("手机号")
    private String phone;

    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty("密码")
    private String psw;

    @NotNull(message = "登录设备不能为空！")
    @ApiModelProperty("设备0-6")
    private Integer device;
}
