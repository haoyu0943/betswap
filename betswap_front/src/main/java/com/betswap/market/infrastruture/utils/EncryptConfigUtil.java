package com.betswap.market.infrastruture.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptConfigUtil {
    public static void main(String[] args) {

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt
        textEncryptor.setPassword("Yijiang");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("Yijiang@2021");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}

