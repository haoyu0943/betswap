package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.yijiang.core.LoginInterceptor;


@Clear(LoginInterceptor.class)
public class IndexController extends Controller{
	public void index() {

		render("index.jsp");
	}


}
