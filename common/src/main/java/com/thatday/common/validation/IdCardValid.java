package com.thatday.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义身份证校验注解
 */

@Documented
@Constraint(validatedBy = {IdCardValidtor.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCardValid {

    String message() default "请输入有效的身份证号码！" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
