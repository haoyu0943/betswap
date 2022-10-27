package com.yijiang.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ExceptionInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		try{
			inv.invoke();
		}catch(Exception e){
			//统一对controller中的错误进行处理，不用在controller中加try catch
			e.printStackTrace();
			inv.getController().setAttr("flag", "0");
			inv.getController().renderJson();
		}
		
	}

}
