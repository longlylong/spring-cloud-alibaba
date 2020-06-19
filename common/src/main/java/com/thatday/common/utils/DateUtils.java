/**
 * Copyright &copy; 2012-2016  All rights reserved.
 */
package com.thatday.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.util.*;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = {
			"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
			"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 *
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 *
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 *
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取相差的年份
	 *
	 * @param date
	 * @return
	 */
	public static int pastYears(Date date) {
		long pastDays = pastDays(date);
		return (int) (pastDays / 365);
	}

	/**
	 * 加减指定的天数
	 *
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);

		return c.getTime();
	}

	/**
	 * 指定天的0点
	 *
	 * @return
	 */
	public static Date getDateBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 指定天的结束
	 *
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 一周的星期几
	 * 周末：1，周一：2 ，周二3…… 周六：7
	 *
	 * @return
	 */
	public static int getDayOfWeek() {
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 取周区间日期
	 *
	 * @param weekType 1-本周，2-下周，3-下下周后(90天内)
	 * @return
	 */
	public static List<Date> getWeekSection(int weekType) {
		List<Date> section = new ArrayList<>();
		Date currDay = new Date(), begin = currDay, end = currDay;
		int dayOfWeek = getDayOfWeek() - 1, weekDay = 7, diff = weekDay - dayOfWeek;
		weekType = (weekType < 1 || weekType > 3) ? 1 : weekType;
		switch (weekType) {
			case 1:
				if (dayOfWeek > 0) {
					end = addDay(currDay, diff);
				}
				break;
			case 2:
				if (dayOfWeek == 0) {
					begin = addDay(begin, 1);
					end = addDay(begin, 6);
				} else if (dayOfWeek > 0) {
					begin = addDay(begin, diff + 1);
					end = addDay(begin, 6);
				}
				break;
			case 3:
				if (dayOfWeek == 0) {
					begin = addDay(begin, 8);
					end = addDay(begin, 73);
				} else if (dayOfWeek > 0) {
					begin = addDay(begin, diff + 8);
					end = addDay(begin, 73);
				}
				break;
		}
		begin = getDateBegin(begin);
		end = getDateEnd(end);

		section.add(begin);
		section.add(end);
		return section;
	}


	/**
	 * long 转timeStamp
	 */
	public static Timestamp longTransToTimeStamp(Long longTime) {
		if (longTime == null || longTime == 0) {
			return null;
		}
		Timestamp timestamp = new Timestamp(longTime);
		return timestamp;
	}

	/**
	 * 获取当天的开始时间
	 */
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}


	/**
	 * 获取当天的结束时间
	 */
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取昨天的开始时间
	 */
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取昨天的结束时间
	 */
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取明天的开始时间
	 */
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 获取明天的结束时间
	 */
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取本周的开始时间
	 */
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本周的结束时间
	 */
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}


	/**
	 * 获取本月的开始时间
	 */
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	/**
	 * 获取本月的结束时间
	 */
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	/**
	 * 获取本年的开始时间
	 */
	public static java.util.Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		// cal.set
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);


		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本年的结束时间
	 */
	public static java.util.Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	/**
	 * 获取某个日期的开始时间
	 */
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某个日期的结束时间
	 */
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取今年是哪一年
	 */
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	/**
	 * 获取本月是哪一月
	 */
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	/**
	 * 两个日期相减得到的天数
	 */
	public static int getDiffDays(Date beginDate, Date endDate) {

		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("getDiffDays param is null!");
		}

		long diff = (endDate.getTime() - beginDate.getTime())
				/ (1000 * 60 * 60 * 24);

		int days = new Long(diff).intValue();

		return days;
	}

	/**
	 * 两个日期相减得到的毫秒数
	 */
	public static long dateDiff(Date beginDate, Date endDate) {
		long date1ms = beginDate.getTime();
		long date2ms = endDate.getTime();
		return date2ms - date1ms;
	}

	/**
	 * 获取两个日期中的最大日期
	 */
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return beginDate;
		}
		return endDate;
	}

	/**
	 * 获取两个日期中的最小日期
	 */
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return endDate;
		}
		return beginDate;
	}

	/**
	 * 返回某月该季度的第一个月
	 */
	public static Date getFirstSeasonDate(Date date) {
		final int[] SEASON = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = SEASON[cal.get(Calendar.MONTH)];
		cal.set(Calendar.MONTH, sean * 3 - 3);
		return cal.getTime();
	}

	/**
	 * 返回某个日期下几天的日期
	 */
	public static Date getNextDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
		return cal.getTime();
	}

	/**
	 * 返回某个日期前几天的日期
	 */
	public static Date getFrontDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
		return cal.getTime();
	}

	/**
	 * 获取某个年月的开始时间
	 */
	public static Date getBeginTime(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate localDate = yearMonth.atDay(1);
		LocalDateTime startOfDay = localDate.atStartOfDay();
		ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 获取某个年月的结束时间
	 */
	public static Date getEndTime(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate endOfMonth = yearMonth.atEndOfMonth();
		LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
		return Date.from(zonedDateTime.toInstant());
	}
}
