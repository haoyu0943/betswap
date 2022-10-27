package com.yijiang.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.kit.StrKit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharUtils;
import org.codehaus.plexus.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TzStringUtils {

	private final static String DES = "DES";
	private final static String ENCODE = "GBK";
	private final static String defaultKey = "NzP7yjWOfeF9DBgjlP";
	private final static String walletKey = "JZetyySpUndWjMBEg";

	public static String checkNull(Object obj){
		if(obj==null){
			return "";
		}
		else{
			return obj.toString();
		}
	}

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
		return TzStringUtils.formatDoubleToString((num/totalCount)*100,format)+"%";
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
	
	public static String base64Encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return new BASE64Encoder().encode(b);
	}
	
	/**
	 * md5加密
	 * 方法名：md5Base64<BR>
	 * @param str
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String md5Base64(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return base64Encode(md5.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * md5加强加密
	 * 方法名：saltPassword<BR>
	 * @param slatString
	 * @param password
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String saltPassword(String slatString,String password){
		return md5Base64(slatString+password);
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
		if (StrKit.notBlank(num)) {
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
			if (StrKit.notBlank(arr[i])) {
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
		}else if (StrKit.isBlank(tar)) {
			return false;
		}
		
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equalsIgnoreCase(tar)) {
				return true;
			}
		}
		return false;
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
	

	public static String propertyToField(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();

		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();

	}
	/**

	 * 字段转换成对象属性 例如：user_name to userName

	 * @param field

	 * @return

	 */
	public static String fieldToProperty(String field) {

		if (null == field) {
			return "";
		}
		char[] chars = field.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {

			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}

		}
		return sb.toString();
	}

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

	public static String Md5Encrypt(String controllername,String methodname) {
		String strkey = "@#$xingyun^*123!";
		String dateFormat = "yyyyMMdd";
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		StringBuilder sb = new StringBuilder();
		try {
			String dataStr = controllername+methodname+sdf.format(date)+strkey;
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

	public static String DencryptString (String value, char secret) {
		String newresult=value;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			String newvalue = new String(decoder.decodeBuffer(value), "UTF-8");
			byte[] bt = newvalue.getBytes();
			for (int i = 0; i < bt.length; i++) {
				bt[i] = (byte) (bt[i] ^ (int) secret);
			}
			newresult = new String(bt, 0, bt.length);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return newresult;
	}

	public static String EncryptString(String value, char secret) {
		String newresult=value;
		try {
			byte[] bt=value.getBytes();
			for(int i=0;i<bt.length;i++) {
				bt[i]=(byte)(bt[i]^(int)secret);
			}
			newresult = new String(bt, 0, bt.length);
			System.out.println("before base64 newresult="+newresult);
			byte[] textByte = newresult.getBytes("UTF-8");
			System.out.println(new String(textByte));
			BASE64Encoder encoder = new BASE64Encoder();
			newresult=encoder.encode(bt);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return newresult;
	}

	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        System.out.println(new String(encrypted));
		//return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(encrypted);
	}

	// 解密
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			//byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] encrypted1=decoder.decodeBuffer(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static BigDecimal decryptWal(String data) throws IOException, Exception {
		if (data == null)
			return null;
		byte[] buf = org.apache.commons.codec.binary.Base64.decodeBase64(data);
		byte[] bt = decrypt(buf, walletKey.getBytes(ENCODE));
		String str= new String(bt, ENCODE);
		return new BigDecimal(str);
	}

	public static String encryptWal(BigDecimal data) throws Exception {
		byte[] bt = encrypt(String.valueOf(data).getBytes(ENCODE), walletKey.getBytes(ENCODE));
		String strs = Base64.encodeBase64String(bt);
		return strs;
	}

	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	public static void main(String[] args) {
//		long start = System.currentTimeMillis();
//		for (int i = 1; i <= 1000; i++) {
//			System.out.println("====="+i);
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("一共耗费:"+(end-start)+"毫秒");

//		System.out.println("323423dfsdf3".matches("\\d*"));
//		System.out.println(TzStringUtils.formatDoubleToString(12.251978D,"#.##"));
//		System.out.println(numberToABC("27"));
		//System.out.println(getDwmcOrDwdm("陕西省咸阳市公安局秦都分局【610402000123】",1));
		//System.out.println(getDwmcOrDwdm("陕西省咸阳市公安局秦都分局【610402000123】",2));

		String controllername="OpenBlindboxController";
		String methodname="index";
		String jmtoken=TzStringUtils.Md5Encrypt(controllername, methodname);
		System.out.println("jmtoken："+jmtoken);
		char secret='8'; //加密文字符
		String pass="1111111111111111111";
		System.out.println("原字符串内容："+pass);
		String key="1234567890123456";
		try {
			String encryptResult = Encrypt(pass, key);
			System.out.println("加密后的内容：" + encryptResult);
			String uncryptResult = Decrypt(encryptResult, key);
			System.out.println("解密后的内容：" + uncryptResult);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


}
