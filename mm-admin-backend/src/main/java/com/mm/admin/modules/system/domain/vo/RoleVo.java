package com.mm.admin.modules.system.domain.vo;

import com.mm.admin.modules.system.domain.Role;
import com.thatday.common.token.UserInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RoleVo extends Role {

    @NotNull(message = "网关授权失败!")
    private UserInfo userInfo;

}
