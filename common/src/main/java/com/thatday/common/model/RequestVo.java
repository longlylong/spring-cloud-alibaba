package com.thatday.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thatday.common.token.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求的基类 其他类必须集成这个
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestVo implements Serializable {

    //这里地方的UserInfo是网关注入的,网关会鉴权
    @ApiModelProperty(hidden = true)
    private UserInfo userInfo;
}
