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
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
public class IdGen {

    private static final SecureRandom random = new SecureRandom();

    private static final long startTime = DateUtil.parseDate("2020-07-01").getTime();

    private static volatile int num = 100000;
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
    public static String getNextTime() {
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
        if (num >= 999999) {
            num = 100000;
        } else {
            int i = random.nextInt(4) + 1;
            num = num + i;
        }
        return num;
    }

    private static final HashMap<String, ArrayList<Integer>> offsetMap = new HashMap<>();
    private static final HashMap<String, Long> lastIdMap = new HashMap<>();

    //随机自增的需要提供上一个id
    public static synchronized Long autoId(String key, Long lastId) {
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

    public static void main(String[] args) {
        System.out.println(getNextTimeCode());
        System.out.println(getNextTimeCode());
        System.out.println(getNextTimeCode(17200));
        System.out.println(getNextTimeCode(7200));
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
        System.out.println(IdUtil.getSnowflake(1, 2).nextIdStr());
    }

}
