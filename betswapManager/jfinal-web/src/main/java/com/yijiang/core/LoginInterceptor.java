package com.yijiang.core;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.yijiang.common.ConstantList;
import com.yijiang.model.SysuserModel;
import com.yijiang.model.UserModel;

public class LoginInterceptor implements Interceptor {
	
	public static ConcurrentHashMap<String, Date> timeMap;
	static{
		if(timeMap == null){
			timeMap = new ConcurrentHashMap<String, Date>();
		}
	}
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		SysuserModel userSession = (SysuserModel) controller.getSession().getAttribute("userSession");
		UserModel h5userSession=(UserModel)controller.getSession().getAttribute(ConstantList.USER_H5_SESSION);
		if(userSession != null){
			//System.out.println("have userSession");
			timeMap.put(userSession.getStr("id"), new Date());
			inv.invoke();
		}
		else if(h5userSession!=null){
			//System.out.println("have h5userSession");
			timeMap.put(h5userSession.getStr("id"), new Date());
			inv.invoke();
		}
		else if(inv.getActionKey().equals("/extsysRoute")||inv.getActionKey().equals("/sendMsg")||inv.getActionKey().equals("/receiveMsg")||inv.getActionKey().equals("/offlinemap")){
			//System.out.println("special url");
			inv.invoke();
		}
		else{
			//System.out.println("have no session");
			String header = controller.getRequest().getHeader("X-Requested-With");  
			if("XMLHttpRequest".equals(header)){
				controller.setAttr("nosession", "n");
				controller.renderJson();
			}
			else{
				controller.redirect("/login");
			}
		}
	}

	public static ConcurrentHashMap<String, Date> getTimeMap() {
		return timeMap;
	}

	public static void setTimeMap(ConcurrentHashMap<String, Date> timeMap) {
		LoginInterceptor.timeMap = timeMap;
	}
}
