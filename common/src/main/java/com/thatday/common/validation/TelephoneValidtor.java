package com.thatday.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义手机号校验规则
 */

public class TelephoneValidtor implements ConstraintValidator<TelephoneValid, String> {
    @Override
    public void initialize(TelephoneValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }

        String pattern = "^[1][3-9][0-9]{9}$";
        return value.matches(pattern);

    }
}
