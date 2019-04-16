package com.thatday.common.validation;

import com.thatday.common.model.RequestVo;
import com.thatday.common.utils.TokenUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckTokenValidityValidator implements ConstraintValidator<CheckTokenValidity, RequestVo.UserInfo> {


    @Override
    public boolean isValid(RequestVo.UserInfo userInfo, ConstraintValidatorContext constraintValidatorContext) {
        return TokenUtil.checkToken(userInfo.getToken());
    }
}
