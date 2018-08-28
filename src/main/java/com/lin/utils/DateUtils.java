package com.lin.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 对日期进行转换处理的工具类。
 *
 * @author Jeesea
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String PATTERN_DATE_SHORT = "yyyyMMdd";

	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String PATTERN_DATE = "yyyy-MM-dd";

	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	
	public static final String PATTERN_DATE_TIME2 = "yyyy-MM-dd HH:00:00";
	
	
	/**
	 * 日期格式：dd/MM/yyyy
	 */
	public static final String PATTERN_DATE_2 = "dd/MM/yyyy";

	/**
	 * 日期格式：yyyy/MM/dd
	 */
	public static final String PATTERN_DATE_3 = "yyyy/MM/dd";

	/**
	 * 日期格式：dd/MM/yyyy HH:mm:ss
	 */
	public static final String PATTERN_DATE_TIME_2 = "dd/MM/yyyy HH:mm:ss";

	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String PATTERN_DATE_TIME_FULL = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 日期格式：yyyy-MM
	 */
	public static final String PATTERN_YEAR_MONTH = "yyyy-MM";
	
	public static final String PATTERN_YEAR_MONTH2 = "yyyy/MM";
	
	/**
	 * 日期格式：HH:mm
	 */
	public static final String PATTERN_HOUR_MINUTE = "HH:mm";

	/**
	 * 日期格式：MM/dd HH:mm
	 */
	public static final String PATTERN_MONTH_DAY_HOUR = "MM/dd HH:mm";
	
	/**
	 * 日期格式：MM/dd
	 */
	public static final String PATTERN_MONTH_DAY = "MM/dd";
	
	/**
	 * 日期格式：MM/dd
	 */
	public static final String PATTERN_HOUR_MINUTE_S = "HH:mm:ss";
	
	/**
	 * 当天的510分钟转换为8:30:00
	 * 
	 **/
	public static String intTimeToString(Integer startTime, Integer time) {
		if (startTime == null) {
			startTime = 0;
		}
		time = time.intValue() + startTime.intValue();
		if (time != null && time.intValue() <= 1440 && time.intValue() >= 0) {
			int hour = time.intValue() / 60;
			int minute = time.intValue() % 60;

			String hourStr = StringUtils.EMPTY;
			String minuteStr = StringUtils.EMPTY;
			if (hour < 10) {
				hourStr = "0" + hour;
			} else {
				hourStr = "" + hour;
			}
			if (minute < 10) {
				minuteStr = "0" + minute;
			} else {
				minuteStr = "" + minute;
			}
			return hourStr + ":" + minuteStr + ":00";
		}else if(time.intValue()>=1440){
			int hour = (time.intValue()-1440) / 60;
			int minute = time.intValue() % 60;

			String hourStr = StringUtils.EMPTY;
			String minuteStr = StringUtils.EMPTY;
			if (hour < 10) {
				hourStr = "0" + hour;
			} else {
				hourStr = "" + hour;
			}
			if (minute < 10) {
				minuteStr = "0" + minute;
			} else {
				minuteStr = "" + minute;
			}
			return hourStr + ":" + minuteStr + ":00";
		}
		return "";
	}


	
	public static String format(Calendar cal, String pattern) {
		if (cal == null) {
			return null;
		}

		return DateFormatUtils.format(cal, pattern);
	}

	/**
	 * 将日期时间置为23点59分59秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date last(Date date) {
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
	}

	/**
	 * 将日期时间置为0点0分0秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date begin(Date date) {
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		return date;
	}

	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		return DateFormatUtils.format(date, pattern);
	}

	public static String formatToDate(Calendar cal) {
		if (cal == null) {
			return null;
		}

		return DateFormatUtils.ISO_DATE_FORMAT.format(cal);
	}

	public static String formatToDate(Date date) {
		if (date == null) {
			return null;
		}

		return DateFormatUtils.ISO_DATE_FORMAT.format(date);
	}

	public static String formatToDateTime(Calendar cal) {
		return format(cal, PATTERN_DATE_TIME);
	}

	public static String formatToDateTime(Date date) {
		return format(date, PATTERN_DATE_TIME);
	}

	/**
	 * 获取某天的开始时间。
	 */
	public static Date getDateBegin(Date date) {
		if (date == null) {
			return null;
		}

		return truncate(date, Calendar.DATE);
	}



	/**
	 * 获取某天的结束时间。
	 */
	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 获取当前月的第一天的开始时间。
	 */
	public static Date getMonthBegin() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return getMonthBegin(year, month);
	}

	/**
	 * 获取某年中某月的第一天的开始时间。
	 */
	public static Date getMonthBegin(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		return getDateBegin(cal.getTime());
	}



	/**
	 * 获取当前月的最后一天的结束时间。
	 */
	public static Date getMonthEnd() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return getMonthEnd(year, month);
	}

	/**
	 * 获取某年中某月的最后一天的结束时间。
	 */
	public static Date getMonthEnd(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getDateEnd(cal.getTime());
	}

	/**
	 * 根据日期对象获取月份。
	 *
	 * <p>
	 * <strong>返回值说明：</strong>
	 * <ul>
	 * <li>当 date 为 null 时，返回 0</li>
	 * <li>其他情况返回 1-12 的数字</li>
	 * </ul>
	 */
	public static int getMonth(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据日期对象获取星期几，根据中国人的传统将星期一作为星期的第一天。
	 *
	 * <p>
	 * <strong>返回值说明：</strong>
	 * <ul>
	 * <li>当 date 为 null 时，返回 0</li>
	 * <li>其他情况返回 1-7 的数字</li>
	 * <li>其中，星期一是 1，星期天是 7</li>
	 * </ul>
	 */
	public static int getChineseWeek(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == 1 ? 7 : dayOfWeek - 1;
	}

	/**
	 * 根据日期对象获取当周是一年中的第几周，根据中国人的传统将星期一作为星期的第一天。
	 *
	 * <p>
	 * <strong>返回值说明：</strong>
	 * <ul>
	 * <li>当 date 为 null 时，返回 0</li>
	 * <li>其他情况返回周次的数字</li>
	 * </ul>
	 */
	public static int getChineseWeekOfYear(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static Date parse(String str, String pattern) {
		if (StringUtils.isBlank(str)) {
			return null;
		}

		Date result = null;
		try {
			result = parseDate(str, pattern);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static Date parseByDate(String str) {
		return parse(str, PATTERN_DATE);
	}

	public static Date parseByDateTime(String str) {
		return parse(str, PATTERN_DATE_TIME);
	}

	public static Date parseByDate2(String str) {
		return parse(str, PATTERN_DATE_2);
	}

	public static Date parseByDate3(String str) {
		return parse(str, PATTERN_DATE_3);
	}

	public static Date parseByDateTime2(String str) {
		return parse(str, PATTERN_DATE_TIME_2);
	}

	/**
	 * 将秒数格式化为可读的时间。例如：08:58
	 */
	public static String secondsToDisplayTime(long seconds) {
		if (seconds < 0) {
			return "00:00";
		}

		long day = TimeUnit.SECONDS.toDays(seconds);
		long hour = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

		StringBuilder time = new StringBuilder();
		if (day != 0) {
			time.append(StringUtils.leftPad(String.valueOf(day), 2, "0") + ":");
		}
		if (hour != 0) {
			time.append(StringUtils.leftPad(String.valueOf(hour), 2, "0") + ":");
		}
		time.append(StringUtils.leftPad(String.valueOf(minute), 2, "0") + ":");
		time.append(StringUtils.leftPad(String.valueOf(second), 2, "0"));

		return time.toString();
	}

	/**
	 * 将秒转成小时分钟秒并展示
	 * @param duration
	 * @return
	 */
	public static String toDisplayString(long duration){
		long hour = duration / (60 * 60) ; 
		long minute = (duration - (hour * 60 * 60)) / 60 ; 
		long second = duration % 60 ; 
		StringBuilder buf = new StringBuilder();
		
		if(hour > 0){ 
		   buf.append(hour).append("h");
		}
		
		if(minute > 0){
			buf.append(minute).append("m");	
		}
		
		if(minute > 0){
			buf.append(second).append("s");	
		}		
		
		return buf.toString();
	}
	
	/**
	 * 获得当天从零点以来的分钟数
	 * @return
	 */
	public static int getCurrentMinuteFromBegin(){
		Calendar cal = Calendar.getInstance() ; 
		int nowMinutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
		return nowMinutes ; 
	}	
	
	/**
	 * 将时间规约到指定的小时
	 * @param cal   - 日期
     * @param hourOfDay
     * @return
	 */
	public static Calendar restrictTime2Hour(Calendar cal , int hourOfDay){
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal ; 
	}

	public static void main(String[] arg) {
		Date date = DateUtils.parseByDateTime("2016-08-26 12:22:36");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

//		System.out.println(sdf.format(date));
		
		
		System.out.println(intTimeToString(0,1200));
		System.out.println(getCurrentMinuteFromBegin());
		System.out.println(new Date().getMinutes());
	}
}
