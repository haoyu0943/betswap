package com.yijiang.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.jfinal.kit.StrKit;



/**
 * 编号定义
 * @author zsp
 *
 */
public class NumberDefine {
	private static Random random = new Random();
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
	private static SimpleDateFormat sdfhf=new SimpleDateFormat("yyyyMMddHHmmss");  
	private static SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHHmmssEEE");  
	private static SimpleDateFormat sdf3=new SimpleDateFormat("yyyyMMddHHmmssSSS");  

	
	/**
	 * 用户编号生成
	 */
	public static String getUserNumber(String dwdm) {
		String str="P"+dwdm+sdf.format(new Date())+(random.nextInt(899)+100); 
		return str;
	}

	
	/**
	 * 流转记录编号
	 * @return
	 */
	public static String getKqjlbh(String bht) {
		String str=bht+sdf3.format(new Date())+(random.nextInt(899)+100); 
		return str;
	}

	
	/**
	 * 附件任务附件主键编号
	 * 方法名：getFjrwbh<BR>
	 * 创建人：qiaobo <BR>
	 * 时间：2017年5月23日-上午11:06:42 <BR>
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getFjrwbh() {
		String str="fr"+sdfhf.format(new Date())+(random.nextInt(899)+100); 
		return str;
	}
	
	/**
	 * 记录编号
	 * 方法名：getJlbh<BR>
	 * 创建人：qiaobo <BR>
	 * 时间：2017年5月24日-下午3:46:48 <BR>
	 * @param prefix
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getJlbh(String prefix) {
		if (StrKit.isBlank(prefix))prefix = "";
		String str=prefix+sdfhf.format(new Date())+(random.nextInt(899)+100); 
		return str;
	}

	public static long genUUID_suffix(int suffix) {
		long s = random.nextLong();
		s = s << 3;
		s = s + suffix;
		return s;
	}

	
	public static void main(String[] args) {
//		System.out.println(getJlbh("BK"));
		System.out.println(getJlbh("aaa"));
	}
	
	public static int getRandomNumb(int maxval) {
	    int min=1;
		//Random r = new Random(1);
		//return  r.nextInt(maxval);
	    int ran = (int) (Math.random()*(maxval-min)+min); 
	    return ran;
	}
	
	public static String getRisePercent(int curval,int pastval){
		String format = "#.#";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		//double b=0;
		String rs="";
		if(curval==0){
		   rs = "∞";
		}
		else{
		  if(pastval==curval){
		     rs = "持平";
		  }
		  else if(curval>pastval){
			 float tf=(curval-pastval)/(float)curval*100;
		     //b=tf*100;
		     rs = "上升了"+decimalFormat.format(tf)+"%";
		  }
		  else{
			 float tf=(pastval-curval)/(float)curval*100;
		     //b=tf*100;
		     rs = "减少了"+decimalFormat.format(tf)+"%";
		  }
		}
		//System.out.println("curval="+curval+",pastval="+pastval);
		//System.out.println(rs);
		return rs;
	}	
	
	public static String getPercent(int num,int totalCount){
		String format = "#.#";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		float tf=num/(float)totalCount*100;
		return decimalFormat.format(tf)+"%";
	}
}
