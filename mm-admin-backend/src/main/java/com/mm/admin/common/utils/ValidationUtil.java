package com.mm.admin.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.mm.admin.common.exception.BadRequestException;
import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证工具
 */
public class ValidationUtil {

    public static UserInfo getUserInfo(HttpServletRequest request) {
        String authorization = request.getHeader(TokenConstant.TOKEN);
        return TokenUtil.getUserInfo(authorization);
    }

    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + " 不存在: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }
}
