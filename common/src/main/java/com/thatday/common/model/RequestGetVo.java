package com.thatday.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thatday.common.token.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 请求的基类 其他类必须集成这个
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestGetVo implements Serializable {

    @ApiModelProperty(value = "用户id", hidden = true)
    @NotNull(message = "网关授权失败!")
    private Long userId;

    @ApiModelProperty(value = "角色", hidden = true)
    @NotNull(message = "网关授权失败!")
    private Integer roleId;

    @ApiModelProperty(value = "设备", hidden = true)
    @NotNull(message = "网关授权失败!")
    private String deviceId;

    @ApiModelProperty(value = "accessToken", hidden = true)
    @NotNull(message = "网关授权失败!")
    private String accessToken;

    @ApiModelProperty(value = "过期时间", hidden = true)
    @NotNull(message = "网关授权失败!")
    private Long expireTime;

    @ApiModelProperty(hidden = true)
    public UserInfo getUserInfo() {
        return UserInfo.create(userId, roleId, deviceId, accessToken, expireTime);
    }

}
