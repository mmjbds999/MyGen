package com.hy.tools.uitl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static int getNowWeek() {  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(new Date());  
		return cal.get(Calendar.DAY_OF_WEEK); 
	}
	
	public static String getNowWeekC() {  
		int week = getNowWeek();
		String w = "";
		switch (week) {
		case 2:
			w = "星期一";
			break;
		case 3:
			w = "星期二";
			break;
		case 4:
			w = "星期三";
			break;
		case 5:
			w = "星期四";
			break;
		case 6:
			w = "星期五";
			break;
		case 7:
			w = "星期六";
			break;
		case 1:
			w = "星期天";
			break;

		default:
			break;
		}
		return w; 
	}
	
	public static long getQuot(String time1, String time2){
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse( time1 );
			Date date2 = ft.parse( time2 );
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}
	
	static public String getNowTime(String format){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据时间获取查询条件的开始时间
	 * */
	public static String getTimeBegin(String time){
		String timeBegin = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(time);
			timeBegin = convertDate(date,"yyyyMMdd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return timeBegin+"000000";
	}
	
	/**
	 * 根据时间获取查询条件的结束时间
	 * */
	public static String getTimeEnd(String time){
		String timeEnd = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(time);
			timeEnd = convertDate(date,"yyyyMMdd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return timeEnd+"999999";
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	static public String getNowTime(){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			String format = "yyyyMMddHHmmss";
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	static public String getNowTimeSt(){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			String format = "yyyy年MM月dd日";
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	static public String getNowTime1(){
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			String format = "yyyyMMdd";
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDate(Date date, String format) {
		try {
			if (date != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				if (format == null || format.equals("")) {
					format = "yyyy-MM-dd HH:mm:ss";
				}
				SimpleDateFormat f = new SimpleDateFormat(format);
				return f.format(c.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 字符串转日期
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS"); 
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 毫秒转时间
	 * @author mmjbds999
	 * @param date
	 * @return
	 */
	public static String convertDate(String date){
		if(date.contains("Date(")){
			date = StringUtil.getMarkString(date, "Date(", ")");
		}
		if(date!=null&&!"".equals(date)){
			DateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.parseLong(date));
			return formatter.format(calendar.getTime());
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		TimeUtil.convertDate("Date(1320076800000)");
	}
}
