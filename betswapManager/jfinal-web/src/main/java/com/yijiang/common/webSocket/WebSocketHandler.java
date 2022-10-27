package com.yijiang.common.webSocket;


import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import com.jfinal.handler.Handler;

public class WebSocketHandler extends Handler{
	public WebSocketHandler() {  
        // TODO Auto-generated constructor stub  
    }  
  
    @Override  
    public void handle(String target, HttpServletRequest request,  
            HttpServletResponse response, boolean[] isHandled) {  
        //ServerPush不是一个jfinal管理的类，所以对于@ServerEndpoint(value = "/socket/{uid}")，需要拦截到请求并且放开请求
        int index = target.indexOf("/socket");  
        if (index == -1) {  
        	next.handle(target, request, response, isHandled);  
        }  
    }  
    
}
