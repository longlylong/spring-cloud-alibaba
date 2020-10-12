package com.thatday.user.wx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "无感登录vo")
public class CommonLoginVo {

    @NotNull(message = "code不能为空!")
    @ApiModelProperty(value = "code", required = true)
    private String code;

}
