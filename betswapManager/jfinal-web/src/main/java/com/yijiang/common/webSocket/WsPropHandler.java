package com.yijiang.common.webSocket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class WsPropHandler  extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		int msgpropIndexOf = target.indexOf("/msgprop");
		if (msgpropIndexOf == -1) {
			next.handle(target, request, response, isHandled);  
		}
	}
}
