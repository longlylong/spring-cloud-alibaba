package com.thatday.common.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thatday.common.exception.GlobalException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @ApiModelProperty(value = "用户id", hidden = true)
    private String userId;

    @ApiModelProperty(value = "角色", hidden = true)
    private String role;

    @ApiModelProperty(value = "设备id", hidden = true)
    private Integer deviceId;

    @ApiModelProperty(value = "accessToken", hidden = true)
    private String accessToken;

    @ApiModelProperty(value = "过期时间", hidden = true)
    private Long expireTime;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Long createTime;

    @ApiModelProperty(hidden = true)
    public Long getLongUserId() {
        try {
            return Long.parseLong(userId);
        } catch (Exception e) {
            throw GlobalException.createParam("token转换错误");
        }
    }

    public static UserInfo create(String userId) {
        return create(userId, "", -1);
    }

    public static UserInfo create(String userId, String role, Integer deviceId) {
        return create(userId, role, deviceId, TokenUtil.getAccessToken(userId, role, deviceId),
                new Date().getTime() + TokenUtil.Token_Expires);
    }

    public static UserInfo create(String userId, String role, Integer deviceId, String accessToken, Long expireTime) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRole(role);
        userInfo.setDeviceId(deviceId);
        userInfo.setAccessToken(accessToken);
        userInfo.setExpireTime(expireTime);
        userInfo.setCreateTime(new Date().getTime());
        return userInfo;
    }


}
