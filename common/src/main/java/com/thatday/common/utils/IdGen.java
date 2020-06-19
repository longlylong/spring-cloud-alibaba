/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

	public static String getNextCode() {
		long seconds = (System.currentTimeMillis() - startTime) / 1000;
		long day = seconds / (86400);
		long m = seconds % (86400);
		return (day * 10000 + m / 10) + String.format("%04d", nextInt());
	}

	private static synchronized int nextInt() {
		if (num >= 9999) {
			num = 1;
		} else {
			int i = random.nextInt(10) + 1;
			num = num + i;
		}
		return num;
	}

	public static void main(String[] args) {
		System.out.println(getNextCode());
	}

}
