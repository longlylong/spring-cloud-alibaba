package com.mm.admin.modules.system.domain.vo;

import com.mm.admin.common.base.BaseRequestVo;
import lombok.Data;

/**
 * 修改密码的 Vo 类
 */
@Data
public class UserPassVo extends BaseRequestVo {

    private String oldPass;

    private String newPass;
}
