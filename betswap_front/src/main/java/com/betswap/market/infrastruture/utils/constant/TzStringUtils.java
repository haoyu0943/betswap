package com.betswap.market.infrastruture.utils.constant;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TzStringUtils {

	/**
	 * 将一个日期转换成String 
	 * 方法名：formatDateToString<BR>
	 * @param date
	 * @param pattern
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String formatDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期字符串转换成Date 方法名：getDateString<BR>
	 * 时间：2016年10月21日-下午3:35:06 <BR>
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException Date<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static Date parseStringToDate(String dateString, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(dateString);
	}

	/**
	 * 将小数格式化成字符串，会进行四舍五入 如：3656.4554===结果:3656.46<BR>
	 * 方法名：formatDoubleToString<BR>
	 * @param dou
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String formatDoubleToString(double dou,String format) {
		if(isEmpty(format))format = "#.##";
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String string = decimalFormat.format(dou);// 四舍五入，逢五进一
		return string;
	}
	
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
	
	/**
	 * 非空判断
	 * 方法名：isNotEmpty<BR>
	 * @param str
	 * @return boolean<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	
	/**
	 * 百分比转换
	 * 方法名：getPercent<BR>
	 * @param num
	 * @param totalCount
	 * @param objects
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String getPercent(int num,double totalCount,String...objects){
		String format = "#.##";
		if(objects!=null && objects.length>0){
			format = objects[0];
		}
		return formatDoubleToString((num/totalCount)*100,format)+"%";
	}	
	
	
	/**
	 * 冒泡排序方法,如果为true那就是降序，false那么久是升序 
	 * 方法名：sorts<BR>
	 * @param datas
	 * @param flag
	 * @return int[]<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static int[] sorts(int[] datas,boolean flag){
		for (int i = 0; i < datas.length; i++) {//轮询次数
			for(int j=0; j < datas.length-1; j++){//交换次数
				if(flag){ 
					if(datas[j] < datas[j+1]){
						int temp = datas[j];
						datas[j] = datas[j+1];
						datas[j+1] = temp;
					}
				}else{
					if(datas[j] < datas[j+1]){
						int temp = datas[j];
						datas[j] = datas[j+1];
						datas[j+1] = temp;
					}
				}
			}
		}
		return datas;
	}
	
	/**
	 * 凯德加密
	 * 方法名：encryption<BR>
	 * @param str
	 * @param k
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String encryption(String str,int k){
		String string = "";
		for (int i = 0; i < str.length(); i++) {
			char c= str.charAt(i);
			if(c>='a' && c<='z'){
				c += k%26;
				if(c<'a'){
					c+=26;
				}
				if(c>'z'){
					c-=26;
				}
			}else if(c>='A' && c<='Z'){
				c+=k%26;
				if(c<'A'){
					c+=26;
				}
				if(c>'Z'){
					c-=26;
				}
			}
			string+=c;
		}
		return string;
	}
	
	/**
	 * 凯德解密
	 * 方法名：dencryption<BR>
	 * @param str
	 * @param n
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dencryption(String str,int n){
		String string = "";
		int k = Integer.parseInt("-"+n);
		for (int i = 0; i < str.length(); i++) {
			char c= str.charAt(i);
			if(c>='a' && c<='z'){
				c += k%26;
				if(c<'a'){
					c+=26;
				}
				if(c>'z'){
					c-=26;
				}
			}else if(c>='A' && c<='Z'){
				c+=k%26;
				if(c<'A'){
					c+=26;
				}
				if(c>'Z'){
					c-=26;
				}
			}
			string+=c;
		}
		return string;
	}
	
	
	
	/**
	 * 文件后缀处理
	 * @param oldExt
	 * @return
	 */
	public static String ext(String oldExt){
		String result =oldExt;
		if(!oldExt.equals("") && oldExt!=null){
			if(oldExt.toLowerCase().equals("xlsx") || oldExt.toLowerCase().equals("xlsx"))result = "xls";
			if(oldExt.toLowerCase().equals("docx") || oldExt.toLowerCase().equals("doc"))result = "word";
		}
		return result;
	}
	
	public static boolean isImage(String ext){
		return ext.toLowerCase().matches("jpg|gif|bmp|png|jpeg");
	}
	
	public static boolean isDoc(String ext){
		return ext.toLowerCase().matches("doc|docx|xls|xlsx|pdf|txt|ppt|pptx|rar|zip|html|jsp|sql|htm|shtml|xml");
	}
	
	public static boolean isVideo(String ext){
		return ext.toLowerCase().matches("mp4|flv|mp3|rmbv|avi");
	}

	public static final String TrimDwZero(String code){
    	String rs=code;
    	//不足12位，补满12位
    	if(code.length()<12){
    		for(int i=0;i<12-code.length();i++){
    			rs+="0";
    		}
    	}
    	else{
    	   rs=rs.substring(0,12); 	
    	}
    	//进行截取0的操作
    	if(rs.substring(2).equals("0000000000")){
    	   rs=rs.substring(0,2);
    	}
    	else{
    	   if(rs.substring(4).equals("00000000")){
    		   rs=rs.substring(0,4); 
    	   }
    	   else{
    		   if(rs.substring(6).equals("000000")){
        		   rs=rs.substring(0,6); 
        	   }
    		   else{
    			   if(rs.substring(9).equals("000")){
            		   rs=rs.substring(0,9); 
            	   }    			   
    		   }
    		   
    	   }
    	}
    	return rs;
    }
	
	/**
	 * 将1-26转成a-z
	 * 方法名：numberToABC<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String numberToABC(String num){
		String[] xcpcNames = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		if (StringUtils.isNotBlank(num)) {
			int index = Integer.valueOf(num) - 1;
			if (index >=0 && index < 26) {
				return xcpcNames[index];
			}else {
				return num;
			}
		}
		return "";
	}
	
	public static String ToCH(int intInput) {  
		String si = String.valueOf(intInput);  
		String sd = "";  
		if (si.length() == 1){  
		    sd += GetCH(intInput);  
		    return sd;  
		} else if (si.length() == 2){  
		    if (si.substring(0, 1).equals("1")){
		    	sd += "十";  
		    }else{
			    sd += (GetCH(intInput / 10) + "十");  
	            sd += ToCH(intInput % 10);  
		    }  
		}  
		return sd;  
	}  
	  
    private static String GetCH(int input) {  
        String sd = "";  
        switch (input) {  
        case 1:  
            sd = "首";  
            break;  
        case 2:  
            sd = "二";  
            break;  
        case 3:  
            sd = "三";  
            break;  
        case 4:  
            sd = "四";  
            break;  
        case 5:  
            sd = "五";  
            break;  
        case 6:  
            sd = "六";  
            break;  
        case 7:  
            sd = "七";  
            break;  
        case 8:  
            sd = "八";  
            break;  
        case 9:  
            sd = "九";  
            break;  
        default:  
            break;  
        }  
        return sd;  
    }  
	
	/**
	 * 把数组转成in的参数
	 * 方法名：arrToInStr<BR>
	 * @param arr
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String arrToInStr(String[] arr){
		String fjbh = "";
		if (arr == null || arr.length <= 0) {
			return "''";
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (StringUtils.isNotBlank(arr[i])) {
				if (i > 0) {
					fjbh += ",'"+arr[i]+"'";
				}else {
					fjbh += "'"+arr[i]+"'";
				}
			}
		}
		return fjbh;
	}
	
	/**
	 * 字符串数组包含
	 * @param arr
	 * @param tar
	 * @return
	 */
	public static boolean arrContains(String[] arr, String tar){
		if (arr == null) {
			return false;
		}else if (arr.length <= 0) {
			return false;
		}else if (StringUtils.isBlank(tar)) {
			return false;
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equalsIgnoreCase(tar)) {
				return true;
			}
		}
		return false;
	}

	public static String checkNull(Object obj){
		if(obj==null){
			return "";
		}
		else{
			return obj.toString();
		}
	}
	
	//将类似的这个的格式-陕西省咸阳市公安局秦都分局【610402000000】，将单位和代码取出来
	//flg=1要名称，flg=2要代码
	public static String getDwmcOrDwdm(String inpustr,int flg){
		if(inpustr==null||inpustr.equals("")||inpustr.indexOf("【")==-1||inpustr.indexOf("【")==-1){
			return "";
		}
		else{
			int p=inpustr.indexOf("【");
			String dwmc=inpustr.substring(0,p);
			String dwdm=inpustr.substring(p+1,inpustr.length()-1);
			if(flg==1){
				return dwmc;
			}
			else{
				return dwdm;
			}
		}
	}
	
	public static void main(String[] args) {
		String str=Md5Encrypt("qryUserFromblockchain");
		System.out.println("Str+++++++"+str);
//		long start = System.currentTimeMillis();
//		for (int i = 1; i <= 1000; i++) {
//			System.out.println("====="+i);
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("一共耗费:"+(end-start)+"毫秒");
		
//		System.out.println("323423dfsdf3".matches("\\d*"));
//		System.out.println(TzStringUtils.formatDoubleToString(12.251978D,"#.##"));
//		System.out.println(numberToABC("27"));
		System.out.println(getDwmcOrDwdm("陕西省咸阳市公安局秦都分局【610402000123】",1));
		System.out.println(getDwmcOrDwdm("陕西省咸阳市公安局秦都分局【610402000123】",2));
	}

	public static String Md5Encrypt(String methodname) {
		String strkey = "@h#u$Yijiang^*2021!JwMcd98";
		String dateFormat = "yyyyMMdd";
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		StringBuilder sb = new StringBuilder();
		try {
			String dataStr = methodname+sdf.format(date)+strkey;
			//System.out.println(dataStr);
			String rs = new String(dataStr);
			MessageDigest md = MessageDigest.getInstance("MD5");
			rs = bytesToHexString(md.digest(rs.getBytes("UTF-8")));
			return rs;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	//将分割符分开的字符串转变为sql中in的条件字符串
	public static String chageStrtoSQLins(String str,String slipstr){
		String[] strarray=str.split(slipstr);
		String rs="";
		if(str.indexOf(slipstr)==-1){
			rs=str;
		}
		else {
			for (int i = 0; i < strarray.length; i++) {
				if (!strarray[i].trim().equals("")) {
					rs += "'" + strarray[i] + "',";
				}
			}
			if (!rs.equals("")) {
				//去掉最后一个逗号
                rs=rs.substring(0,rs.length()-1);
			}
		}
		return rs;
	}

	public static String TrimZero(String instr){
		String temps=instr;
		while(temps.endsWith("0")){
			temps=temps.substring(0,temps.length()-1);
		}
		return temps;
	}

	//单位代码：省（2位）+地市（2位）+区县（2位）+警种部门（3位）+更小单位的编码（3位）,返回不为0的前缀
	//例：输入330480000000，返回330480
	public static String GetUnitCodePrefix(String unitcode){
		if(unitcode==null || unitcode.length()!=12){
			return "";
		}
		int end=2;
		if(!unitcode.substring(2, 4).equals("00")){
			end=4;
			if(!unitcode.substring(4, 6).equals("00")){
				end=6;
				if(!unitcode.substring(6, 9).equals("000")){
					end=9;
					if(!unitcode.substring(9,12).equals("000")){
						end=12;
					}
				}
			}
		}
		return unitcode.substring(0,end);
	}
}
