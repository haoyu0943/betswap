package com.yijiang.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.kit.StrKit;

public class IPAddress {

	private static String getIp() {
		String ipStr = "";
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()){ 
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					ip = addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address){
						if(!ip.getHostAddress().startsWith("127")){
							ipStr = ip.getHostAddress();
						}
//						System.out.println("本机的IP = " + ip.getHostAddress());
					} 
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ipStr;
	}
		
	public static String getLocalIp(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        if(ip.equals("127.0.0.1")){
        	ip = getIp();
        }
        if(StrKit.isBlank(ip)){
        	ip = getIpAddress(request);
        }
        return ip;
    }
	
	public static String getIpAddress(){
		InetAddress inetAddress = null;
		String ipAddress="127.0.0.1";
		try {
		   inetAddress = InetAddress.getLocalHost();
		   ipAddress = inetAddress.getHostAddress();
		   if(null != ipAddress && ipAddress.length() > 15){
		      if(ipAddress.indexOf(",") > 0){
		         ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		      }
	       }
		} 
		catch (UnknownHostException e) {
	       e.printStackTrace();
		}
		return ipAddress;
	}
	
	public static String getIpAddress(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			 ipAddress = request.getHeader("Proxy-Client-IP");
	    }
	    if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
		   ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		   ipAddress = request.getRemoteAddr();
		   if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
			  //根据网卡获取本机配置的IP地址
			  InetAddress inetAddress = null;
			  try {
			     inetAddress = InetAddress.getLocalHost();
			  } 
			  catch (UnknownHostException e) {
		         e.printStackTrace();
			  }
	          ipAddress = inetAddress.getHostAddress();
		   }
		}
		//对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
		if(null != ipAddress && ipAddress.length() > 15){
		            //"***.***.***.***".length() = 15
			if(ipAddress.indexOf(",") > 0){
			   ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	public static void main(String[] args) throws SocketException {
		System.out.println(IPAddress.getIp());
	}
}
