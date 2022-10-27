package com.yijiang.core;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yijiang.common.ConstantList;
import org.apache.log4j.Logger;

import com.jfinal.handler.Handler;
import com.yijiang.util.IPAddress;

public class JspSkipHandler extends Handler{
	Logger logger = Logger.getLogger(JspSkipHandler.class);
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		
		int indexJsp = target.lastIndexOf(".jsp");
		//int indexHtml = target.lastIndexOf(".html");
		if(indexJsp > -1){
			if(request.getSession().getAttribute("userSession") == null&&request.getSession().getAttribute(ConstantList.USER_H5_SESSION) == null){
				logger.debug("没有登录，访问jsp::"+target);
				System.out.println("没有登录，访问jsp::"+target);
				try {
					logger.debug(request.getContextPath());
					response.sendRedirect(request.getContextPath()+"/login");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			//System.out.println("let's go");
			nextHandler.handle(target, request, response, isHandled);
		}
		/*
		if(target.equals("/login") || target.equals("/login.jsp")){
			try {
				response.sendRedirect(request.getContextPath()+"/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		if(target.equals("/") 
				|| target.indexOf("/login") > -1
				|| target.indexOf("/login.jsp") > -1 ){
			String ipstr = IPAddress.getLocalIp(request);
			request.getSession().setAttribute("clientIP", ipstr);
		}
	}

}
