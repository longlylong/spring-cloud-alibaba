/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

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

	private static final long startTime = DateUtils.parseDate("2020-06-01").getTime();

	private static volatile int num;

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	//根据时间生成的数据库id
	public static String getNextTimeCode() {
		long seconds = (System.currentTimeMillis() - startTime) / 1000;
		long day = seconds / (86400);
		long m = seconds % (86400);
		return (day * 10000 + m / 10) + String.format("%04d", nextTimeInt());
	}

	private static synchronized int nextTimeInt() {
		if (num >= 9999) {
			num = 1;
		} else {
			int i = random.nextInt(10) + 1;
			num = num + i;
		}
		return num;
	}

	private static HashMap<String, ArrayList<Integer>> offsetMap = new HashMap<>();
	private static HashMap<String, Long> lastIdMap = new HashMap<>();

	//随机自增的
	public static synchronized Long autoId(String key, Long lastId) {
		int i = random.nextInt(20) + 1;
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
