package com.betswap.market.infrastruture.utils.Md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

public class Md5Crypt {

//	/**利用MD5进行加密
//     * @param str  待加密的字符串
//     * @return  加密后的字符串
//     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
//     * @throws UnsupportedEncodingException
//     */
//    public static String EncoderByMd5(String str) {
//        //确定计算方法
//    	String newstr = "";
//		try {
////			MessageDigest md5=MessageDigest.getInstance("MD5");
////			BASE64Encoder base64en = new BASE64Encoder();
//			//加密后的字符串
////			newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return newstr;
//    }

    public static String EncoderByMd5(String str) {
        //确定计算方法
    	String newstr = "";
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			//加密后的字符串
			newstr=encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return newstr;
    }
	/**
	 * @param bytes
	 * @return
	 */
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 *
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(final byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(EncoderByMd5("XZXT"));//ZwsUcorZkCrsujLiL6T2vQ==
	}
}
