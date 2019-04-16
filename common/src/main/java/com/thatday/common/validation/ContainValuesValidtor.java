package com.thatday.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 包含值校验
 */

public class ContainValuesValidtor implements ConstraintValidator<ContainValuesValid, String> {
    @Override
    public void initialize(ContainValuesValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        } else {
            String[] a = getValues();
            if (null != a) {
                return Arrays.asList(a).contains(value);
            } else {
                return false;
            }
        }
    }

    private String[] getValues() {
        String name = ContainValuesValid.class.getName();
        // 提取到被注解的方法Method，这里用到了反射的知识
        Method method;
        try {
            method = Class.forName(name).getDeclaredMethod("ContainValuesValid");
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        // 从Method方法中通过方法getAnnotation获得我们设置的注解
        ContainValuesValid annotation = method.getAnnotation(ContainValuesValid.class);
        return annotation.values();
    }
}
