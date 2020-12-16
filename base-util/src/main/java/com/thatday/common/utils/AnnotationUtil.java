package com.thatday.common.utils;


import cn.hutool.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

public class AnnotationUtil extends cn.hutool.core.annotation.AnnotationUtil {

    public static <A extends Annotation> void initPackage(String packageName, Class<A> annotationType, BiConsumer<Class<?>, A> consumer) {
        ClassUtil.scanPackageByAnnotation(packageName, annotationType).forEach(clazz -> {
            consumer.accept(clazz, clazz.getAnnotation(annotationType));
        });
    }
}
