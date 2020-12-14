package com.thatday.common.utils;


import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.function.BiConsumer;

public class AnnotationUtil extends cn.hutool.core.annotation.AnnotationUtil {

    public static <A extends Annotation> void initPackage(String packageName, Class<A> annotationType, BiConsumer<Class<?>, A> consumer) throws ClassNotFoundException {
        URL resource = SpringUtil.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        if (resource == null) {
            throw new RuntimeException("the " + packageName + " is not found");
        }
        File file = new File(resource.getFile());
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        for (File classFile : listFiles) {
            if (classFile.isDirectory()) {
                initPackage(packageName + "." + classFile.getName(), annotationType, consumer);
            } else if (classFile.getName().endsWith(".class")) {
                getAnnotation(packageName, classFile, annotationType, consumer);
            }
        }
    }

    private static <A extends Annotation> void getAnnotation(String packageName, File f, Class<A> annotationType, BiConsumer<Class<?>, A> consumer) throws ClassNotFoundException {
        String clazzPath = packageName + "." + f.getName().replace(".class", "");
        Class<?> clazz = SpringUtil.getClassLoader().loadClass(clazzPath);
        consumer.accept(clazz, clazz.getAnnotation(annotationType));
    }
}
