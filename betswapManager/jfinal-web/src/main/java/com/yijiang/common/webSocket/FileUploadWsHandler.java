package com.yijiang.common.webSocket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class FileUploadWsHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		int fileupwsIndexOf = target.indexOf("/fileupws");
		if (fileupwsIndexOf == -1) {
			next.handle(target, request, response, isHandled);  
		}
	}
}
