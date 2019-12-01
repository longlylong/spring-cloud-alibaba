package com.thatday.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义包含值校验
 */

@Documented
@Constraint(validatedBy = {ContainValuesValidtor.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainValuesValid {

    String message() default "请输入有效的值！" ;

    String[] values();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
