package com.betswap.market.infrastruture.utils.helper.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class DESEncryptHelper {
    public static final String ALGORITHM = "DES";

    private static Key toKey(byte[] key) {
        SecretKey secretKey = null;
        try {
            if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
                DESKeySpec dks = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
                secretKey = keyFactory.generateSecret(dks);
            } else {
                // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
                secretKey = new SecretKeySpec(key, ALGORITHM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public static byte[] DESEncrpyt(byte[] data,String secret){
        try {
            Key k = toKey(secret.getBytes());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] DESDecrpyt(byte[] token,String secret){
        try {
            Key k = toKey(secret.getBytes());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);
            return cipher.doFinal(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String DESEncrpyt(String data,String secret){
        try {
            Key k = toKey(secret.getBytes());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String DESDecrpyt(String token,String secret){
        try {
            Key k = toKey(secret.getBytes());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, k);
            byte[] result_byte = cipher.doFinal(Base64.decodeBase64(token));
            return new String(result_byte, StandardCharsets.UTF_8);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
