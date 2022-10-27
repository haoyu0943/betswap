package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.SysuserModel;
import com.yijiang.service.SysuserService;
import com.yijiang.util.TmFunctions;


@Clear(LoginInterceptor.class)
public class UserregController extends Controller {
    public void index(){
        String yqm=getPara("yqm");
        String lan=getPara("lan");
        setAttr("yqm",yqm);
        setAttr("lan",lan);
        renderJsp("index.jsp");
    }
}
