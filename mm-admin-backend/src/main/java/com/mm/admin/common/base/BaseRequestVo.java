package com.mm.admin.common.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thatday.common.token.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequestVo implements Serializable {

    @ApiModelProperty(hidden = true)
    private UserInfo userInfo;
}
