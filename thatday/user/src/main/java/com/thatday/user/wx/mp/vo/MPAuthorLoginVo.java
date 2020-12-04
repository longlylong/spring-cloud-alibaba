package com.thatday.user.wx.mp.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MPAuthorLoginVo {

    @NotEmpty(message = "code不能为空")
    @ApiModelProperty(value = "code", required = true)
    private String code;
}
