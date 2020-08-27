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

    @ApiModelProperty(value = "角色", hidden = true)
    private String role;

    @ApiModelProperty(value = "设备id", hidden = true)
    private Integer deviceId;

    @ApiModelProperty(value = "accessToken", hidden = true)
    private String accessToken;

    @ApiModelProperty(value = "过期时间", hidden = true)
    private Long expireTime;

    public static UserInfo create(Long userId) {
        return create(userId, "", -1);
    }

    public static UserInfo create(Long userId, String role, Integer deviceId) {
        return create(userId, role, deviceId, TokenUtil.getAccessToken(userId, role, deviceId),
                new Date().getTime() + TokenUtil.Token_Expires);
    }

    public static UserInfo create(Long userId, String role, Integer deviceId, String accessToken, Long expireTime) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRole(role);
        userInfo.setDeviceId(deviceId);
        userInfo.setAccessToken(accessToken);
        userInfo.setExpireTime(expireTime);
        return userInfo;
    }


}
