package com.mm.admin.modules.system.domain.vo;

import com.mm.admin.modules.system.domain.User;
import com.thatday.common.token.UserInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserVo extends User {

    @NotNull(message = "网关授权失败!")
    private UserInfo userInfo;

}
