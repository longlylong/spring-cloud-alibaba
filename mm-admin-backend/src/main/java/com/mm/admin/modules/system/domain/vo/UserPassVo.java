package com.mm.admin.modules.system.domain.vo;

import com.thatday.common.model.RequestPostVo;
import lombok.Data;

/**
 * 修改密码的 Vo 类
 */
@Data
public class UserPassVo extends RequestPostVo {

    private String oldPass;

    private String newPass;
}
