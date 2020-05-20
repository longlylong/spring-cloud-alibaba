package com.thatday.common.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "角色id", hidden = true)
    private Integer roleId;

    @ApiModelProperty(value = "设备id", hidden = true)
    private Integer deviceId;

    @ApiModelProperty(value = "accessToken", hidden = true)
    private String accessToken;

    @ApiModelProperty(value = "过期时间", hidden = true)
    private Long expireTime;
}