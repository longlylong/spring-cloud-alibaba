package com.thatday.user.modules.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginWeChatVo {

    @NotBlank(message = "openId不能为空！")
    private String openId;
}
