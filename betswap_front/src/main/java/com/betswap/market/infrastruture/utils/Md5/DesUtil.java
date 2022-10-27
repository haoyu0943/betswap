package com.betswap.market.infrastruture.utils.Md5;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密 解密算法
 * @author zhangdi
 *
 */
public class DesUtil {

    private final static String DES = "DES";
    private final static String ENCODE = "GBK";
    private final static String defaultKey = "NzP7yjWOf!@#$%eF9DBgjlP";
    private final static String walletKey = "JZetyySpUndWjMBEg";

    /**
     * 使用 默认key 加密
     * @param data 待加密数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
        String strs = Base64.encodeBase64String(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     * @param data 待解密数据
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }


    /**
     * 使用 默认key 加密
     * @param data 待加密数据
     * @return
     * @throws Exception
     */
    public static String encryptWal(BigDecimal data) throws Exception {
        byte[] bt = encrypt(String.valueOf(data).getBytes(ENCODE), walletKey.getBytes(ENCODE));
        String strs = Base64.encodeBase64String(bt);
        return strs;
    }

    /**
     * 使用 默认key 解密
     * @param data 待解密数据
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static BigDecimal decryptWal(String data) throws IOException, Exception {
        if (data == null)
            return null;
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(buf, walletKey.getBytes(ENCODE));
        String str= new String(bt, ENCODE);
        return new BigDecimal(str);
    }




    /**
     * Description 根据键值进行加密
     * @param data 待加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        String strs =  Base64.encodeBase64String(bt);
        return strs;
    }

    /**
     * 根据键值进行解密
     * @param data 待解密数据
     * @param key    密钥
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key
     *            加密键byte数组
     * @return
     * @throws Exception
     */
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

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
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

    public static void main(String[] args) throws Exception {
//        String data = "5c18519cc07456eeaf43135316edf8df7f298ccbb46ad450a2309c7c4c1b30e9";
//        System.out.println(">>>>>>>>>>默认秘钥加密后："+encrypt(data));
//        System.out.println(">>>>>>>>>>默认秘钥加密后："+encrypt(data));

        System.out.println(">>>>>>>>>>默认秘钥加密后7.60："+encryptWal(new BigDecimal("")));
        System.out.println(">>>>>>>>>>默认秘钥加密后7.60："+decryptWal(encryptWal(new BigDecimal(10578.56))));



    }

}