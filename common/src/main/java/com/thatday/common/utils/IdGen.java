/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
public class IdGen {

    private static SecureRandom random = new SecureRandom();

    private static final long startTime = DateUtil.parseDate("2020-07-01").getTime();

    private static volatile int num;
    private final static String baseNum = "0123456789";
    private final static String baseString = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //使用SecureRandom随机生成Long
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    //7位随机字符
    public static String getRandom7String() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int number = random.nextInt(baseString.length());
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    //4位随机数
    public static String getRandom4Num() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(baseNum.length());
            sb.append(baseNum.charAt(number));
        }
        return sb.toString();
    }

    //UUID中间无-分割
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //根据时间生成的数据库id
    public static String getNextTimeCode() {
        long seconds = (System.currentTimeMillis() - startTime) / 1000;
        long day = seconds / (86400);
        long m = seconds % (86400);
        return String.format("%04d", day) + fixedLength(m, 6) + String.format("%04d", nextTimeInt());
    }

    private static String fixedLength(long num, int n) {
        String s = num + "";
        if (s.length() >= n) {
            return s;
        }
        return s + String.format("%1$0" + (n - s.length()) + "d", 0);
    }
    private static synchronized int nextTimeInt() {
        if (num >= 9999) {
            num = 1;
        } else {
            int i = random.nextInt(5) + 1;
            num = num + i;
        }
        return num;
    }

    private static HashMap<String, ArrayList<Integer>> offsetMap = new HashMap<>();
    private static HashMap<String, Long> lastIdMap = new HashMap<>();

    //随机自增的需要提供上一个id
    public static synchronized Long autoId(String key, Long lastId) {
        int i = random.nextInt(5) + 1;
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

    public static void main(String[] args) {
        System.out.println(getNextTimeCode());
    }

}
