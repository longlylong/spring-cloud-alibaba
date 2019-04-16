package com.thatday.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义身份证校验规则
 */

public class IdCardValidtor implements ConstraintValidator<IdCardValid, String> {
    @Override
    public void initialize(IdCardValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }

        String pattern = "^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$";
        return value.matches(pattern);
    }
}
