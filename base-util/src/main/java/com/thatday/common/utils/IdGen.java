/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
public class IdGen extends IdUtil {

    public static void main(String[] args) {
        System.out.println(TimeId.getNextTimeCode());
        System.out.println(TimeId.getNextTimeCode());
        System.out.println(IdUtil.objectId());
        System.out.println(IdUtil.objectId());
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
    }

    private static final SecureRandom random = new SecureRandom();

    //时间序列id 多个服务的话用这个 port为每个服务的端口号
    public static String getNextTimeCode(int workerId, int datacenterId) {
        return TimeId.getNextTimeCode(workerId, datacenterId);
    }

    //时间序列id  单体服务的话用这个
    public static String getNextTimeCode() {
        return TimeId.getNextTimeCode();
    }

    //随机自增的需要提供上一个id key建议用class simple name
    public static Long autoId(String key, Long lastId) {
        return AutoAddId.autoRandomAddId(key, lastId);
    }

    //7位随机字符
    public static String getRandom7Str() {
        return RandomId.getRandom7Str();
    }

    //4位随机数
    public static String getRandom4Num() {
        return RandomId.getRandom4Num();
    }

    private final static class RandomId {

        private final static String baseNum = "0123456789";
        private final static String baseString = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static String getRandom7Str() {
            return getRandomObj(7, baseString);
        }

        public static String getRandom4Num() {
            return getRandomObj(4, baseNum);
        }

        private static String getRandomObj(int size, String base) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
            return sb.toString();
        }
    }

    private final static class AutoAddId {

        private static final HashMap<String, ArrayList<Integer>> offsetMap = new HashMap<>();
        private static final HashMap<String, Long> lastIdMap = new HashMap<>();

        //随机自增的需要提供上一个id
        public static synchronized Long autoRandomAddId(String key, Long lastId) {
            int i = random.nextInt(4) + 1;
            Long lastLastId = lastIdMap.get(key);

            lastIdMap.put(key, lastId);
            ArrayList<Integer> integers = offsetMap.computeIfAbsent(key, k -> new ArrayList<>());

            if (lastId.equals(lastLastId)) {
                for (Integer integer : integers) {
                    lastId += integer;
                }
            } else {
                integers.clear();
            }
            integers.add(i);
            return lastId + i;
        }
    }

    private final static class TimeId {

        //多个服务的话用这个
        public static String getNextTimeCode(int workerId, int datacenterId) {
            return IdUtil.getSnowflake(workerId, datacenterId).nextIdStr();
        }

        //单体服务的话用这个
        public static String getNextTimeCode() {
            return IdUtil.getSnowflake(1, 1).nextIdStr();
        }
    }
}
