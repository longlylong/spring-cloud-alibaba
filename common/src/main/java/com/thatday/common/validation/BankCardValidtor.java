package com.thatday.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义银行卡号校验规则
 */

public class BankCardValidtor implements ConstraintValidator<BankCardValid, String> {
    @Override
    public void initialize(BankCardValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        return true;//BankCardUtil.checkBankCard(value);
    }
}
