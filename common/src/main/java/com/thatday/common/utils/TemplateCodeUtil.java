package com.thatday.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TemplateCodeUtil {

    /**
     * 对象集合转换，两个对象的属性名字需要一样
     */
    public static <T, E> List<T> transTo(List<E> fromList, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> toList = new ArrayList<>();
        for (E e : fromList) {
            T entity = clazz.newInstance();
            BeanUtils.copyProperties(entity, e);
            toList.add(entity);
        }
        return toList;
    }


    /**
     * 对象集合转换，两个对象的属性名字需要一样，并可自定义设置一些参数
     */
    public static <T, E> List<T> transTo(List<E> fromList, Class<T> clazz, OnTransListener<T, E> onTransListener) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        List<T> toList = new ArrayList<>();
        for (E e : fromList) {
            T entity = clazz.newInstance();
            BeanUtils.copyProperties(entity, e);
            if (onTransListener != null) {
                onTransListener.doSomeThing(entity, e);
            }
            toList.add(entity);
        }
        return toList;
    }

    /**
     * 对象转换，E转为t对象
     */
    public static <T, E> T transTo(E e, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T t = clazz.newInstance();
        BeanUtils.copyProperties(t, e);
        return t;
    }

    /**
     * 接口常量转为指定类型的List
     */
    public static <T> List<T> interfaceTransToVal(Class<?> clazz, Class<T> toClazz) throws IllegalAccessException {
        List<T> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            T t = (T) field.get(clazz);
            if (t.getClass() == toClazz) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * json 转为对象
     */
    public static <T> T jsonToObject(byte[] jsonByte, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        T t = null;
        try {
            t = objectMapper.readValue(jsonByte, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Map 转为对象，字段格式要一致
     */
    public static <T> T mapTrasnToObject(Map<String, Object> map, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(map);
        T t = objectMapper.readValue(jsonStr, clazz);
        return t;
    }

    /**
     * 集合根据某个关键字进行分组
     */
    public static <T> Map<String, List<T>> groupBy(List<T> tList, StringKey<T> stringKey) {
        Map<String, List<T>> map = new HashMap<>();
        for (T t : tList) {
            if (map.containsKey(stringKey.key(t))) {
                map.get(stringKey.key(t)).add(t);

            } else {
                List<T> list = new ArrayList<>();
                list.add(t);
                map.put(stringKey.key(t), list);
            }
        }
        return map;
    }

    /**
     * 把对象转为json,并输出到日志中
     */
    public static void logObject(String tag, Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            log.info("{}{}", tag, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.info("exception = {}", e);
        }
    }


    /**
     * 集合分组的关键词
     */
    public interface StringKey<T> {
        String key(T t);
    }


    /**
     * 编写一些额外逻辑
     */
    public interface OnTransListener<T, E> {
        void doSomeThing(T t, E e);
    }


}
