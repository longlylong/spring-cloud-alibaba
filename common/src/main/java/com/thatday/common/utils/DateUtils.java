/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import cn.hutool.core.date.DateUtil;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends DateUtil {

	public static final long MILLIS_PER_DAY = org.apache.commons.lang3.time.DateUtils.MILLIS_PER_DAY;
	public static final long MILLIS_PER_MINUTE = org.apache.commons.lang3.time.DateUtils.MILLIS_PER_MINUTE;
	public static final long MILLIS_PER_SECOND = org.apache.commons.lang3.time.DateUtils.MILLIS_PER_SECOND;

	public static String[] patterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};


}
