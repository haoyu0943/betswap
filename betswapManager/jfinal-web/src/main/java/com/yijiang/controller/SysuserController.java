package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.SysuserModel;
import com.yijiang.service.SysuserService;
import com.yijiang.util.TmFunctions;


@Clear(LoginInterceptor.class)
public class SysuserController extends Controller {
    private SysuserService userService = new SysuserService();

    @Clear(LoginInterceptor.class)
    public void index(){
        render("userlist.jsp");
    }


    @Clear(LoginInterceptor.class)
    public void inputuser(){
        //System.out.println("-------55555");
        renderJsp("inputuser.jsp");
    }
    @Clear(LoginInterceptor.class)
    public void saveuser(){
        //System.out.println("进入");
        String xm = getPara("xm");
        String xb = getPara("xb");
        String xbname = getPara("xbname");
        String sj = getPara("sj");
        String sfzh = getPara("sfzh");
        SysuserModel sysUserModel=new SysuserModel();
        sysUserModel.set("name",xm).set("sex",xb).set("sexname",xbname).set("idcard",sfzh).set("phone",sj);
        sysUserModel.set("id", TmFunctions.getKeyStr(""));
        sysUserModel.save();
        renderJson();
    }
}
