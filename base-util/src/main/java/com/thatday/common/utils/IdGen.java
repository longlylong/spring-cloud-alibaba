/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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
        System.out.println(TimeId.getNextTimeCode(17200));
        System.out.println(TimeId.getNextTimeCode(7200));
        System.out.println(IdUtil.objectId());
        System.out.println(IdUtil.objectId());
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
    }

    private static final SecureRandom random = new SecureRandom();

    //时间序列id 多个服务的话用这个 port为每个服务的端口号
    public static String getNextTimeCode(int port) {
        return TimeId.getNextTimeCode(port);
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

        private static volatile int num = 100000;
        private static final long startTime = DateUtil.parseDate("2020-07-01").getTime();

        //多个服务的话用这个 port为每个服务的端口号
        public static String getNextTimeCode(int port) {
            //(4位 + 6位) + 5位 + 6位
            return getNextTime() + String.format("%05d", port) + nextTimeInt();
        }

        //单体服务的话用这个
        public static String getNextTimeCode() {
            //(4位 + 6位) + 6位
            return getNextTime() + nextTimeInt();
        }

        //根据时间生成的数据库id
        private static String getNextTime() {
            long seconds = (System.currentTimeMillis() - startTime) / 1000;
            long day = seconds / (86400);
            long m = seconds % (86400);
            //4位 + 6位
            return String.format("%04d", day) + fixedLength(m);
        }

        private static String fixedLength(long num) {
            return StrUtil.fill(num + "", '0', 6, false);
        }

        private static synchronized int nextTimeInt() {
            //还可以判断不同的类序列自增
            //还可以判断同一秒是否到了999999

            if (num >= 999999) {
                num = 100000;
            } else {
                int i = random.nextInt(4) + 1;
                num = num + i;
            }
            return num;
        }
    }
}
