package com.yijiang.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.crypto.Data;

import com.jfinal.kit.StrKit;

/**
 * 日期工具类
 * 
 * TmDateUtil<BR>
 * @version 1.0.0
 *
 */
public class TmDateUtil {
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期转换
	 * 方法名：dateToString<BR>
	 * @param time
	 * @return Date<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static Date dateToString(String time,String foramt){
		Date startTime = null;
		try {
			 startTime = new SimpleDateFormat(foramt).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startTime;
	}
	
	public static String getCurrentTime(){
		Date currenttime = new Date();
		return date2Str(currenttime,DEFAULT_FORMAT);
	}
	
	/**
	 * 
	 * 方法名：getTimeFormat<BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getTimeFormat(String startTime){
		return getTimeFormat(dateToString(startTime,DEFAULT_FORMAT));
	}
	
	/**
	 * 获取日期几分钟前，几年前
	 * 方法名：getTimeFormat<BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getTimeFormat(Date startTime){
		try{
			long startTimeMills = startTime.getTime();
			long endTimeMills = System.currentTimeMillis();
			long diff = (endTimeMills - startTimeMills)/1000;//秒
			long day_diff  = (long) Math.floor(diff/86400);//天
			StringBuffer buffer = new StringBuffer();
			if(day_diff<0){
				return "[error],时间越界...";
			}else{
				if(day_diff==0 && diff<60){
					if(diff==0)diff=1;
					buffer.append(diff+"秒前");
				}else if(day_diff==0 && diff<120){
					buffer.append("1 分钟前");
				}else if(day_diff==0 && diff<3600){
					buffer.append(Math.round(Math.floor(diff/60))+"分钟以前");
				}else if(day_diff==0 && diff<7200){
					buffer.append("1小时前");
				}else if(day_diff==0 && diff<86400){
					buffer.append(Math.round(Math.floor(diff/3600))+"小时前");
				}else if(day_diff==1){
					buffer.append("1天前");
				}else if(day_diff<7){
					buffer.append(day_diff+"天前");
				}else if(day_diff <30){
					buffer.append(Math.round(Math.ceil( day_diff / 7 )) + " 星期前");
				}else if(day_diff >=30 && day_diff<=179 ){
					buffer.append(Math.round(Math.ceil( day_diff / 30 )) + "月前");
				}else if(day_diff >=180 && day_diff<365){
					buffer.append("半年前");
				}else if(day_diff>=365){
					buffer.append(Math.round(Math.ceil( day_diff /30/12))+"年前");
				}
			}
			return buffer.toString();
		}catch(Exception ex){
			return "";
		}
	}
	
	/** 
     * 得到几天后的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfterDays(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
        return now.getTime();  
    }

	/**
	 * 得到几月后的时间
	 *
	 * @param d
	 * @param month
	 * @return
	 */
	public static Date getDateAfterMonths(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTime();
	}

	/**
	 * 得到几年后的时间
	 *
	 * @param d
	 * @param year
	 * @return
	 */
	public static Date getDateAfterYears(Date d, int year) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
		return now.getTime();
	}
    
    /** 
     * 得到几小时后的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfterHours(Date d, int hours) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + hours);
        return now.getTime();
    }
    /** 
     * 得到几分钟后的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfterMinis(Date d, int hours) {  
    	Calendar now = Calendar.getInstance();  
    	now.setTime(d);  
    	now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + hours);  
    	return now.getTime();  
    }
    
    
    public static Date getDateAfterSecds(Date d, int secds) {  
    	Calendar now = Calendar.getInstance();  
    	now.setTime(d);  
    	now.set(Calendar.SECOND, now.get(Calendar.SECOND) + secds);  
    	return now.getTime();  
    }
    
	/**
	 * date转String
	 * 方法名：date2Str<BR>
	 * @param date
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String date2Str(Date date, String format) {
	  if (null != date && !"".equals(date)) {
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(date);
	  }
	  return null;
	}
	/**
	 * java.sql.Timestamp转成字符串
	 * 方法名：timestamp2Str<BR>
	 * @param time
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String timestamp2Str(Timestamp time) {
	  if(null != time && !"".equals(time)){
	    Date date = new Date(time.getTime());
	    return date2Str(date, DEFAULT_FORMAT);
	  }
	  return null;
	}
	
	public static String timestamp2Str(Timestamp time, String format) {
		if(null != time && !"".equals(time)){
		    Date date = new Date(time.getTime());
		    return date2Str(date, format);
		  }
		  return null;
	}
	
	/**
	 * string 转化成 date
	 * @param dateStr 2016-10-27 17:34
	 * @param dateFormat yyyy-mm-dd HH:mm
	 * @return
	 */
	public static Date stringToDate(String dateStr, String dateFormat) {
		Date date = new Date();
		//注意format的格式要与日期String的格式相匹配
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * string 转化成 timestamp
	 * @param dateStr
	 * @return
	 */
	public static Timestamp stringTotimestamp(String dateStr) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	/**
	 * string 转化成 timestamp
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Timestamp stringToTimestamp2(String dateStr, String dateFormat) {
		DateFormat format = new SimpleDateFormat(dateFormat);
		format.setLenient(false);
		//要转换字符串 str_test
		Timestamp ts = null;
		try {
			ts = new Timestamp(format.parse(dateStr).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
	} 
	/**
	 * date 转化成 timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp dateTotimestamp(Date date) {
		String dateStr = date2Str(date, "yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	
	/**
	 * date 转化成 timestamp
	 * @param date
	 * @return
	 */
	public static Timestamp dateTotimestampYMD(Date date) {
		String dateStr = date2Str(date, "yyyy-MM-dd");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			ts = Timestamp.valueOf(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	
	/**
	 * 
	 * 方法名：thisMonthFirstDay<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String thisMonthFirstDay(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		 //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String firstDay = format.format(c.getTime());
        return firstDay;
	}
	
	/**
	 * 获取当前月的最后一天
	 * 方法名：thisMonthLastDay<BR>
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String thisMonthLastDay(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		//获取当前月最后一天
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String lastDay = format.format(ca.getTime());
        return lastDay;
	}
	
	/**
	 * 
	 * string转Timestamp<BR>
	 * 方法名：qbStringToTimestamp<BR>
	 * @param dateStr
	 * @return Timestamp<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static Timestamp qbStrToTimestamp(String dateStr,String format){
		return new Timestamp(dateToString(dateStr, format).getTime());
	}
	/**
	 * 获取当月第一天
	 * @return
	 */
	public static String getFirstDay() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String str = TmDateUtil.date2Str(c.getTime(), "yyyy-MM-dd");
		return str;
	}
	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static String getLastDay() {
		// TODO Auto-generated method stub
		Calendar ca = Calendar.getInstance();    
	    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    String str = TmDateUtil.date2Str(ca.getTime(), "yyyy-MM-dd");
		return str;
	}
	
	/**
	 * @date 2018年6月26日17:51:08
	 * @return
	 */
	public static Date getThisWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 获得当前日期是一个星期的第几天  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
        return cal.getTime();  
    }
	
	/**
	 * 获取上周一的日期
	 * @return
	 */
	public static Date geLastWeekMonday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getThisWeekMonday(date));  
        cal.add(Calendar.DATE, -7);  
        return cal.getTime();  
    }
	
	/**
	 * 获取上周五的日期
	 * @return
	 */
	public static Date geLastWeekFriday(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getThisWeekMonday(date));  
        cal.add(Calendar.DATE, -3);  
        return cal.getTime();  
    }
	
	/**
     * 判断是否是weekend
     *
     * @param sdate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(String sdate) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sdate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * 获取本年度所有的周末日期
     * @return
     */
    public static List<Date> getZmsDate(){
    	ArrayList<Date> zms = new ArrayList<Date>();
    	Calendar date = Calendar.getInstance();
    	int year = date.get(Calendar.YEAR);
    	Calendar calendar=new GregorianCalendar(year,0,1);
    	int i=1;
    	while(calendar.get(Calendar.YEAR)<year+1){
    		calendar.set(Calendar.WEEK_OF_YEAR,i++);
    		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    		if(calendar.get(Calendar.YEAR)==year){
    			zms.add(calendar.getTime());
    		}
    		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
    		if(calendar.get(Calendar.YEAR)==year){
    			zms.add(calendar.getTime());
    		}
    	}
    	return zms;
    }
    //根据任务类型rwlx和完成期限wcqxDate,计算完成时限的数值，保留两位小数
    //区分战时和非战时两种情况
    public static String getWcsx(String rwlx,Date wcqxDate){
		 Date curdate=new Date();
		 DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 DateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 if(rwlx.equals("0")){
			 //战时wcqxDate是精确到分的
			 float hours = (float)(wcqxDate.getTime() - curdate.getTime()) / (1000*3600); 
			 //System.out.println(hours);
			 //取两位小数
			 DecimalFormat decimalFormat=new DecimalFormat(".00");
			 return decimalFormat.format(hours);
		 }
		 else{
			//两个日期都转换成干净的00:00然后计算日期天数
			String s_curdate=sdf.format(curdate);
			String s_wcqxDate=sdf.format(wcqxDate);
			int days=0;
			try{
			   Date d_curdate = sdf1.parse(s_curdate+" 00:00:00");
               Date d_wcqxDate = sdf1.parse(s_wcqxDate+" 00:00:00");
               days = (int) ((d_wcqxDate.getTime() - d_curdate.getTime()) / (1000*3600*24));
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
				days=0;
			}
           return String.valueOf(days);
		 }
	 }
    
    /**
     * 获取本年度所有的周末日期字符串
     * @return
     */
    public static List<String> getZmsDateStr(){
    	ArrayList<String> zms = new ArrayList<String>();
    	Calendar date = Calendar.getInstance();
    	int year = date.get(Calendar.YEAR);
    	Calendar calendar=new GregorianCalendar(year,0,1);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	int i=1;
    	while(calendar.get(Calendar.YEAR)<year+1){
    		calendar.set(Calendar.WEEK_OF_YEAR,i++);
    		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    		if(calendar.get(Calendar.YEAR)==year){
    			zms.add(sdf.format(calendar.getTime()));
    		}
    		calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
    		if(calendar.get(Calendar.YEAR)==year){
    			zms.add(sdf.format(calendar.getTime()));
    		}
    	}
    	return zms;
    }
    
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    
    /**
     * 判断今天和某个日期的天数差，比较的日期格式yyyy-MM-dd HH:mm:ss
     */
    public static int DaysByCurDate(String sdate) {
    	try{
    		Date curdate=new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
    		Date bjdate=sdf.parse(sdate);
    		return differentDaysByMillisecond(curdate,bjdate);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return -9999;
    	}
    }

    //检查两个日期范围是否有重叠，date的样式都是yyyy-mm-dd的模式
    public static boolean IfOverlap(String date1_start,String date1_end,String date2_start,String date2_end){
		 Date t_date1_start=stringToDate(date1_start+" 00:00:00",  DEFAULT_FORMAT);
		 Date t_date1_end=stringToDate(date1_end+" 23:59:59",  DEFAULT_FORMAT);
		 Date t_date2_start=stringToDate(date2_start+" 00:00:00",  DEFAULT_FORMAT);
		 Date t_date2_end=stringToDate(date2_end+" 23:59:59",  DEFAULT_FORMAT);
		 //满足max(A.start,B.start)<=min(A.end,B.end)，即重复
		 //满足A.end< B.start || A.start > B.end，即不重复
		 if(t_date1_end.getTime()<t_date2_start.getTime()||t_date1_start.getTime()>t_date2_end.getTime()){
		 	return false;
		 }
		 else{
		 	return true;
		 }

	}

	public static void main(String[] args) {
		String date1_start="2022-01-01";
		String date1_end="2022-02-01";
		String date2_start="2022-01-19";
		String date2_end="2022-04-01";
		System.out.println(IfOverlap(date1_start,date1_end,date2_start,date2_end));
	}
}
