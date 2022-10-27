package com.betswap.market.infrastruture.utils.helper.token;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class MD5PublicHelper extends JwtPublicHelper{

    protected String secret;
    protected String header;
    protected String methodName = "methodname";

    public boolean isValid(String Token,String methodName){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String today = formatter.format(new Date());
        System.out.println(methodName + today + secret);
        String md5 = calMD5(methodName + today + secret);
        return  md5.equals(Token);
    }


    public static String calMD5(String input) {
        if(input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while(result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
