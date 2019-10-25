package com.thatday.common.validation;

import com.thatday.common.token.TokenUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckTokenValidityValidator implements ConstraintValidator<CheckTokenValidity, String> {

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        return TokenUtil.checkToken(token);
    }
}
