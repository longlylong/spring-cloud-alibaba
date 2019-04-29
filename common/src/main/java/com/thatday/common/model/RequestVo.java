package com.thatday.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thatday.common.constant.StatusCode;
import com.thatday.common.validation.TokenUtil;
import com.thatday.common.validation.CheckTokenValidity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 请求的基类 其他类必须集成这个
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestVo implements Serializable {

    @CheckTokenValidity
    @NotNull(message = StatusCode.Des_User_Info_Null)
    protected UserInfo userInfo;

    public static UserInfo make(Integer uid, Integer role, Integer device, String token) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(uid);
        userInfo.setRoleId(role);
        userInfo.setDevice(device);
        userInfo.setToken(token);
        userInfo.setExpireTime(new Date().getTime() + TokenUtil.Token_Expires);
        return userInfo;
    }

    public static UserInfo make(Integer uid, Integer role, Integer device) {
        return make(uid, role, device, TokenUtil.getUserToken(uid, role, device));
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo implements Serializable {

        @ApiModelProperty(value = "用户id")
        private Integer userId;

        @ApiModelProperty(value = "用户角色")
        private Integer roleId;

        @ApiModelProperty(value = "设备")
        private Integer device;

        @ApiModelProperty(value = "token")
        private String token;

        @ApiModelProperty(value = "过期时间")
        private Long expireTime;
    }
}
