package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.SysuserModel;
import com.yijiang.service.SysuserService;
import com.yijiang.util.TmFunctions;


public class MessageController extends Controller {
    private SysuserService userService = new SysuserService();

    public void index() {
        SysuserModel ry = getSessionAttr("userSession");
        String rytoken=ry.getStr("rongyu_token");
        setAttr("rytoken",rytoken);
        renderJsp("message.jsp");
    }

}
