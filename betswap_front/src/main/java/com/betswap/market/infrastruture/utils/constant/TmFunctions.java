package com.betswap.market.infrastruture.utils.constant;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
		return headstr+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
				+String.format("%0" + 4 + "d",new Random().nextInt(9999));
	}


	public static  String  changeString(Object obj){
		if (obj==null)
			return null;
		return String.valueOf(obj);
	}
	public static  Integer changeInt(Object obj){
		if (obj==null)
			return null;
		return Integer.valueOf(String.valueOf(obj));
	}
	public static  Long changeLong(Object obj){
		if (obj==null)
			return null;
		return Long.valueOf(String.valueOf(obj));
	}
	public static BigDecimal changeBigDecimal(Object obj){
		if (obj==null)
			return null;
		return BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj)));
	}
	public static BigDecimal changeBigDecimalT(Object obj){
		if (obj==null)
			return new BigDecimal(0);
		return BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj)));
	}
	public static boolean changeBoolean(Object obj) {
		if (obj==null)
			return false;
		return Boolean.valueOf(String.valueOf(obj));
	}

	public static Float changFloat(Object obj) {
		if (obj==null)
			return null;
		return Float.valueOf(String.valueOf(obj));
	}

	public static Timestamp changeTimestamp(Object obj) {
		if (obj==null)
			return null;
		return Timestamp.valueOf(String.valueOf(obj));
	}
	public static String sendtoAPI(String url, JSONObject jsonObject, String encoding)throws ParseException, IOException {
		String body = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		httpPost.setEntity(s);
		//System.out.println("请求地址："+url);
//        System.out.println("请求参数："+nvps.toString());
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		CloseableHttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		response.close();
		return body;
	}


}
