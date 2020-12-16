package com.thatday.common.utils;

import com.thatday.common.exception.TDExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassUtil extends cn.hutool.core.util.ClassUtil {

    public static List<Class<?>> getAllClassByInterface(Class<?> clazz) {
        List<Class<?>> list = new ArrayList<>();
        // 判断是否是⼀个接⼝
        if (clazz.isInterface()) {
            try {
                Set<Class<?>> allClass = scanPackage(clazz.getPackage().getName());
                //循环判断路径下的所有类是否实现了指定的接⼝ 并且排除接⼝类⾃⼰
                for (Class<?> c : allClass) {
                    // isAssignableFrom:判定此 Class 对象所表示的类或接⼝与指定的Class
                    // 参数所表示的类或接⼝是否相同，或是否是其超类或超接⼝
                    if (clazz.isAssignableFrom(c)) {
                        if (!clazz.equals(c)) {
                            // ⾃身并不加进去
                            list.add(c);
                        }
                    }
                }
            } catch (Exception e) {
                throw TDExceptionHandler.throwGlobalException("getAllClassByInterface", e);
            }
        }
        return list;
    }
}
