package com.yijiang.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 常量工具类
 * TzConstanst
 * @version 1.0.0
 *
 */
public class TzConstanst {
	
	//用户session
	public static final String SESSION_USERKEY = "user";
	//权限session
	public static final String PERMISSION_SESSION_USERKEY = "permission";
	
	
	
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("id", 1);
		map.put("name", "keke");
		System.out.println(map.get("id"));
		System.out.println(map.get("name"));
		System.out.println("====================Map它的key-value是用一个set集合进行存储的");
		for (Map.Entry<String, Object> entry: map.entrySet()) {
			System.out.println(entry.getKey()+"==="+entry.getValue());
		}
	}
}
