package com.thatday.common.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "角色id", hidden = true)
    private Integer roleId;

    @ApiModelProperty(value = "设备id", hidden = true)
    private String deviceId;

    @ApiModelProperty(value = "accessToken", hidden = true)
    private String accessToken;

    @ApiModelProperty(value = "过期时间", hidden = true)
    private Long expireTime;

    public static UserInfo create(Long userId) {
        return create(userId, -1, "-1");
    }

    public static UserInfo create(Long userId, Integer roleId, String deviceId) {
        return create(userId, roleId, deviceId, TokenUtil.getAccessToken(userId, roleId, deviceId),
                new Date().getTime() + TokenUtil.Token_Expires);
    }

    public static UserInfo create(Long userId, Integer roleId, String deviceId, String accessToken, Long expireTime) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRoleId(roleId);
        userInfo.setDeviceId(deviceId);
        userInfo.setAccessToken(accessToken);
        userInfo.setExpireTime(expireTime);
        return userInfo;
    }


}