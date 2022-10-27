package com.yijiang.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * 
 * JSP函数自定义标签
 * TmFunctions<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月11日-下午9:21:37 <BR>
 * @version 1.0.0
 *
 */
public class TmFunctions {
	/*
	 * 为什么要学习自定义标签：第一灵活，简单，复用性强
	 * 
	 * */

	/**
	 * 截取字符串
	 * 方法名：getTarStr<BR>
	 * 创建人：wangli <BR>
	 * 时间：2018年1月31日16:08:14<BR>
	 * @param oldStr
	 * @param start
	 * @param end
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 * 例子：地方水电费水电费…稿.doc
	 */
	public static String getFjmcTarStr(String oldStr,int start,int end){
		int lastnum=oldStr.lastIndexOf(".");
		String restr;
		if(lastnum==-1){
			if(oldStr.length()>13){
				String showstr=oldStr.substring(0,13)+"…";
				restr= showstr;
			} else{
				restr= oldStr;
			}
		}else{
			String endstr=oldStr.substring(lastnum-1);
			String oldStr2=oldStr.substring(lastnum-1);
			int len2=getLength(oldStr2)+1;//后续长度包含…的长度
			int maxlen=getLength(oldStr);//总长度………

			if(maxlen<=end){//可以完全显示24   25
				restr= oldStr;
			}else{
				int substrnum=getSubtringNum(oldStr,end-len2);
				String showstr=oldStr.substring(0, substrnum)+"…"+endstr;
				restr= showstr;
			}

		}
		return restr;
	}

	public static int getLength(String s) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 2;
			} else {
				// 其他字符长度为0.5
				valueLength += 1;
			}
		}
		//进位取整
		return  valueLength;
	}

	public static int getSubtringNum(String s,int sublen) {
		int valueLength = 0;
		int maxi=0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 2;
			} else {
				// 其他字符长度为0.5
				valueLength += 1;
			}
			if(sublen>0&&valueLength>=sublen){
				maxi=i;
				break;
			}
		}
		//进位取整
		return  maxi;
	}

	/**
	 * 讲字符串转换成大写
	 * 方法名：toUpper<BR>
	 * @param content
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String toUpper(String content){
		if(TzStringUtils.isEmpty(content))return null;
		return content.toUpperCase();
	}
	
	
	/**
	 * 格式化日期的自定义函数
	 * 方法名：dateFormat<BR>
	 * @param dateString
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 * System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy"));年份
		System.out.println(dateFormat("2014-12-14 12:12:12", "MM"));月份
		System.out.println(dateFormat("2014-12-14 12:12:12", "dd"));天
		System.out.println(dateFormat("2014-12-14 12:12:12", "HH:mm:ss"));24小时制
		System.out.println(dateFormat("2014-12-14 12:12:12", "hh:mm:ss"));12小时制
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd"));年月日
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd HH:mm"));年月日 时分
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd HH:mm:ss"));年月日 时分秒
	 */
	public static String dateFormat(String dateString,String format){
		if(TzStringUtils.isEmpty(dateString))return "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
			String ds =  new SimpleDateFormat(format).format(date);
			return ds;
		} catch (ParseException e) {
			return "";
		}
	}
	
	/**
	 *
	 * 方法名：formateDate<BR>
	 * @param date
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String formateDate(Date date,String format){
		if(date==null)return "";
		String ds =  new SimpleDateFormat(format).format(date);
		return ds;
	}
	
	/**
	 * 空判断
	 * 方法名：isEmpty<BR>
	 * @param content
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean isEmpty(String content){
		return TzStringUtils.isEmpty(content);
	}
	
	/**
	 * 非判断
	 * 方法名：isNotEmpty<BR>
	 * @param content
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean isNotEmpty(String content){
		return !isEmpty(content);
	}
	
	
	public static String getWeekChinesee(int week){
		String cweek = "";
		if(week==1)cweek = "一";
		if(week==2)cweek = "二";
		if(week==3)cweek = "三";
		if(week==4)cweek = "四";
		if(week==5)cweek = "五";
		if(week==6)cweek = "六";
		if(week==7)cweek = "日";
		return cweek;
	}
	
	public static String getCharacter(int num){
		String cweek = "";
		if(num==1)cweek = "A";
		if(num==2)cweek = "B";
		if(num==3)cweek = "C";
		if(num==4)cweek = "D";
		if(num==5)cweek = "E";
		if(num==6)cweek = "F";
		if(num==7)cweek = "G";
		if(num==8)cweek = "H";
		if(num==9)cweek = "I";
		if(num==10)cweek = "J";
		if(num==11)cweek = "K";
		if(num==12)cweek = "M";
		if(num==13)cweek = "L";
		if(num==14)cweek = "N";
		if(num==15)cweek = "O";
		if(num==16)cweek = "P";
		if(num==17)cweek = "Q";
		if(num==18)cweek = "R";
		if(num==19)cweek = "S";
		if(num==20)cweek = "T";
		if(num==21)cweek = "U";
		if(num==22)cweek = "V";
		if(num==23)cweek = "W";
		if(num==24)cweek = "X";
		if(num==25)cweek = "Y";
		if(num==26)cweek = "Z";
		return cweek;
	}
	
	/**
	 * 日期具体的几秒钟以前
	 * 方法名：dateString<BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dateToString(String startTime){
		return TmDateUtil.getTimeFormat(startTime);
	}
	
	/**
	 *  日期具体的几秒钟以前
	 * 方法名：dateToString2<BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dateToString2(Date startTime){
		return TmDateUtil.getTimeFormat(startTime);
	}
	
	/**
	 * 将数字转换成对应的中文
	 * 方法名：intToChnNumConverter<BR>
	 * @param num
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String intToChnNumConverter(int num){
	    String resultNumber = null;
	    if(num > 10000 || num < 0){
	        return "";
	    }
	    HashMap chnNumbers = new HashMap();
	    chnNumbers.put(0, "零");
	    chnNumbers.put(1, "一");
	    chnNumbers.put(2, "二");
	    chnNumbers.put(3, "三");
	    chnNumbers.put(4, "四");
	    chnNumbers.put(5, "五");
	    chnNumbers.put(6, "六");
	    chnNumbers.put(7, "七");
	    chnNumbers.put(8, "八");
	    chnNumbers.put(9, "九");
	 
	    HashMap unitMap = new HashMap();
	    unitMap.put(1, "");
	    unitMap.put(10, "十");
	    unitMap.put(100, "百");
	    unitMap.put(1000, "千");
	    int[] unitArray = {1000, 100, 10, 1};
	 
	    StringBuilder result = new StringBuilder();
	    int i = 0;
	    while(num > 0){
	        int n1 = num / unitArray[i];
	        if(n1 > 0){
	            result.append(chnNumbers.get(n1)).append(unitMap.get(unitArray[i]));
	        }
	        if(n1 == 0){
	            if(result.lastIndexOf("零") != result.length()-1){
	                result.append("零");
	            }
	        }
	        num = num % unitArray[i++];
	        if(num == 0){
	            break;
	        }
	    }
	    resultNumber = result.toString();
	    if(resultNumber.startsWith("零")){
	        resultNumber = resultNumber.substring(1);
	    }
	    if(resultNumber.startsWith("一十")){
	        resultNumber = resultNumber.substring(1);
	    }
	    return resultNumber;
	}
	
	/**
	 * 
	 * 方法名：cutContent<BR>
	 * @param content
	 * @param begin
	 * @param end
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String cutContent(String content,int begin,int end){
		if(TzStringUtils.isEmpty(content))return "";
		String start = "";
		String result = "";
		if(content.length() > end){
			start = content.substring(begin,end);
			result = "<span style='display:none;'>"+content.substring(100,content.length())+"</span>";
			return start+result+"&nbsp;&nbsp;<a onclick='tm_show_expand(this)' href='javascript:void(0)'>展开</a>";
		}else{
			return content;
		}
	}
	
	
	public static String countFileSize(long fileSize) {
		String fileSizeString = "";
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			long fileS = fileSize;
			if (fileS == 0) {
				fileSizeString = "0KB";
			} else if (fileS < 1024) {
				fileSizeString = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSizeString = df.format((double) fileS / 1024) + "KB";
			} else if (fileS < 1073741824) {
				fileSizeString = df
						.format(((double) fileS / 1024 / 1024) - 0.01)
						+ "MB";
			} else {
				fileSizeString = df.format((double) fileS / 1024 / 1024 / 1024)
						+ "G";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileSizeString;
	}
	
	
	/**
	 * 获取集合的长度
	 * 方法名：length<BR>
	 * @param list
	 * @return int<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static int getLength(List list){
		if(list==null)return 0;
		return list.size();
	}

	/**
	 * 获取时间戳主键
	 */
	public static String  getKeyStr(String headstr){
		String str=headstr+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//再加上四位随机数
		Random random = new Random();
		str+=random.nextInt(8999)+1000;
		return str;

	}
}
