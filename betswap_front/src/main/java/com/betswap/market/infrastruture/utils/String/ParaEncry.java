package com.betswap.market.infrastruture.utils.String;

import com.betswap.market.infrastruture.utils.Md5.MD5Util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParaEncry {
    private static final String keystr="Yijiang@2021;Hdxjsds";

    public static Boolean checkParaBean(Object obj) throws Exception {
        StringBuffer  joinStr=new StringBuffer();
        String MD5key="";
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {//向上循环 遍历父类
            Field[] field = clazz.getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                if(f.getName().equals("keyToken")){//在结构里，这个是特定的参数名，表示这个是校验参数
                    MD5key=f.get(obj).toString();
                }
                else{
                    joinStr.append(f.getName());
                    if(f.get(obj)!=null&&!"".equals(String.valueOf(f.get(obj)))){
                        joinStr.append(String.valueOf(f.get(obj)));
                    }
                }
                //System.out.println("属性：" + f.getName() + " 值：" + String.valueOf(f.get(obj)));
            }

        }
        String md5= MD5Util.md5(joinStr.toString(),keystr);
        if(md5.equals(MD5key)){
            return true;
        }
        else{
            return false;
        }
    }

    public static Boolean checkParaMap(HashMap map, String MD5key) throws Exception {
        StringBuffer  joinStr=new StringBuffer();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if(entry.getValue()!=null&&!entry.getValue().equals("")){
                joinStr.append(entry.getValue());
            }
        }
        String md5= MD5Util.md5(joinStr.toString(),keystr);
        if(md5.equals(MD5key)){
            return true;
        }
        else{
            return false;
        }
    }
}
