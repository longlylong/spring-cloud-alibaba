package com.thatday.common.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "设备id")
    private Integer deviceId;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "过期时间")
    private Long expireTime;
}